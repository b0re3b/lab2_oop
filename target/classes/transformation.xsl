<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output method="xml" indent="yes"/>

    <xsl:template match="/Bank">
        <Bank>
            <xsl:for-each-group select="Deposit" group-by="Type">
                <DepositGroup type="{current-grouping-key()}">
                    <xsl:copy-of select="current-group()"/>
                </DepositGroup>
            </xsl:for-each-group>
        </Bank>
    </xsl:template>
</xsl:stylesheet>