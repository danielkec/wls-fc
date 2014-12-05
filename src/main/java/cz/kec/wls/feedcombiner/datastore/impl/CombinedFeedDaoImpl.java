package cz.kec.wls.feedcombiner.datastore.impl;

import cz.kec.wls.feedcombiner.datastore.CombinedFeedDao;
import cz.kec.wls.feedcombiner.datastore.InMemoryDataStore;
import cz.kec.wls.feedcombiner.model.CombinedFeed;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of CombinedFeedDao. Because there is no keyset in the
 * InMemoryDataStore, is used InMemoryKeySet which is persisted same way as all
 * combined feeds.
 *
 * @see CombinedFeedDao
 * @see InMemoryKeySet
 * @author Daniel Kec <daniel at kecovi.cz>
 * @since Dec 4, 2014
 */
public class CombinedFeedDaoImpl implements CombinedFeedDao {

    /**
     * InMemoryDataStore to be used by this dao
     */
    private final InMemoryDataStore inMemoryDataStore;

    /**
     * Creates new CombinedFeedDao implementation. With its own keyset above
     * InMemoryStore.
     *
     * @param inMemoryDataStore
     */
    public CombinedFeedDaoImpl(InMemoryDataStore inMemoryDataStore) {
        this.inMemoryDataStore = inMemoryDataStore;
        //because there is no keyset in the InMemoryDataStore
        InMemoryKeySet inMemoryKeySet
                = this.inMemoryDataStore.get(InMemoryKeySet.KEYSET_KEY, InMemoryKeySet.class);
        if (inMemoryKeySet == null) {
            this.inMemoryDataStore.put(InMemoryKeySet.KEYSET_KEY, new InMemoryKeySet());
        }
    }

    @Override
    public List<CombinedFeed> getAllCombinedFeeds() {
        InMemoryKeySet keySet = this.inMemoryDataStore.get(InMemoryKeySet.KEYSET_KEY, InMemoryKeySet.class);
        ArrayList<CombinedFeed> feedList = new ArrayList<CombinedFeed>();
        for (String key : keySet) {
            feedList.add(this.inMemoryDataStore.get(key, CombinedFeed.class));
        }
        return feedList;
    }

    @Override
    public boolean containsCombinedFeed(String title) {
        //reserved title/id for keyset
        if (InMemoryKeySet.KEYSET_KEY.equals(title)) {
            return true;
        }
        InMemoryKeySet keySet = this.inMemoryDataStore.get(InMemoryKeySet.KEYSET_KEY, InMemoryKeySet.class);
        return keySet.contains(title);
    }

    @Override
    public synchronized void createCombinedFeed(CombinedFeed combinedFeed) {
        this.inMemoryDataStore.put(combinedFeed.getName(), combinedFeed);
        InMemoryKeySet keySet = this.inMemoryDataStore.get(InMemoryKeySet.KEYSET_KEY, InMemoryKeySet.class);
        keySet.add(combinedFeed.getName());
        this.inMemoryDataStore.put(InMemoryKeySet.KEYSET_KEY, keySet);
    }

    @Override
    public synchronized boolean updateCombinedFeed(CombinedFeed combinedFeed) {
        if (containsCombinedFeed(combinedFeed.getName())) {
            this.createCombinedFeed(combinedFeed);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public synchronized boolean deleteCombinedFeed(String title) {
        InMemoryKeySet keySet = this.inMemoryDataStore.get(InMemoryKeySet.KEYSET_KEY, InMemoryKeySet.class);
        // return false if there is no feed with suplemented title/id
        if (!keySet.contains(title)) {
            return false;
        }
        this.inMemoryDataStore.put(title, null);
        keySet.remove(title);
        this.inMemoryDataStore.put(InMemoryKeySet.KEYSET_KEY, keySet);
        return true;
    }

}