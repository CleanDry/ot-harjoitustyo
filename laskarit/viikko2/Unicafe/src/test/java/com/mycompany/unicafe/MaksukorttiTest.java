package com.mycompany.unicafe;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class MaksukorttiTest {

    Maksukortti kortti;

    @Before
    public void setUp() {
        kortti = new Maksukortti(10);
    }

    @Test
    public void luotuKorttiOlemassa() {
        assertTrue(kortti!=null);      
    }
    
    @Test
    public void kortinSaldoAlussaOikein() {
        assertEquals(10, kortti.saldo());
    }
    
    @Test
    public void kortinSaldoKasvaaOikeinLadattaessa0() {
        kortti.lataaRahaa(0);
        assertEquals("saldo: 0.10", kortti.toString());
    }
    
    @Test
    public void kortinSaldoKasvaaOikeinLadattaessa() {
        kortti.lataaRahaa(630);
        assertEquals("saldo: 6.40", kortti.toString());
    }
    
    @Test
    public void saldoVaheneeOikein() {
        kortti.otaRahaa(10);
        assertEquals("saldo: 0.0", kortti.toString());
    }
    
    @Test
    public void saldoEivaheneJosRahaaEiOleTarpeeksi() {
        kortti.otaRahaa(40);
        assertEquals("saldo: 0.10", kortti.toString());
    }
    
    @Test
    public void metodiPalautaaTrueJosRahatRiittavat() {
        assertTrue(kortti.otaRahaa(10));
        assertFalse(kortti.otaRahaa(20));
    }
}
