<?xml version="1.0" encoding="UTF-8"?>
<!--
    Document   : atomTohtml.xsl
    Created on : 5. prosinec 2014, 22:30
    Author     : Daniel Kec
    Description:
        Creates html which is served as combined feed HTML form.
-->

<xsl:stylesheet
    version="1.0"
    xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:atom="http://www.w3.org/2005/Atom"
    xmlns:dc="http://purl.org/dc/elements/1.1/">

    <xsl:output method="html" indent="yes"/>

    <xsl:template match="text()"></xsl:template>

    <xsl:template match="atom:entry">
        <h3>
            <a href="{atom:link/@href}">
                <xsl:value-of select="atom:title"/>
            </a>
        </h3>
        <div class="entry-datetime">
            <b>
                <xsl:text>Published: </xsl:text>
            </b>
            <xsl:value-of select="atom:published"/>
            <xsl:text> </xsl:text>
            <b>
                <xsl:text>Updated: </xsl:text>
            </b>
            <xsl:value-of select="atom:updated"/>
        </div>
        <p>
            <xsl:value-of select="atom:summary" disable-output-escaping="yes"/>
        </p>
    </xsl:template>

    <xsl:template match="/atom:feed">
        <html>
            <head>
                <link rel="stylesheet" type="text/css" href="../../../css/main.css" media="screen" />
                <script type='text/javascript' src='../../../js/Main.js' defer="defer"></script>
                <title>
                    <xsl:value-of select="atom:title"/>
                </title>
            </head>
        </html>
        <body>
            <h1>
                <xsl:value-of select="atom:title"/>
            </h1>
            <h4>
                <xsl:value-of select="atom:subtitle"/>
            </h4>
            <div style="overflow: hidden;
                        text-align: left;
                        margin: 0 auto;
                        width: 840px;">
                <div style="float: right;
                            width: 100px;">

                    <a onclick="redirect('json');" href="javascript:void(0);">
                        <xsl:text>JSON</xsl:text>
                    </a>
                    <xsl:text> </xsl:text>
                    <a onclick="redirect('atom');" href="javascript:void(0);">
                        <xsl:text>ATOM</xsl:text>
                    </a>
                </div>
                <div style="overflow: hidden;">
                    <a onclick="redirectToOverView();" href="javascript:void(0);">
                        <xsl:text>Return</xsl:text>
                    </a>
                </div>
            </div>
            <div class="container">
                <xsl:apply-templates/>
            </div>
        </body>
    </xsl:template>
</xsl:stylesheet>
