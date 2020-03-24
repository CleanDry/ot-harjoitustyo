
package com.mycompany.unicafe;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class KassapaateTest {
    
    Kassapaate kassa;
    Maksukortti kortti;
    
    public KassapaateTest() {
    }
    
    @Before
    public void setUp() {
        kassa = new Kassapaate();
        kortti = new Maksukortti(400);
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void kassassaAluksiOikeaMaaraRahaa() {
        assertEquals(100000,kassa.kassassaRahaa());
    }
    
    @Test
    public void kassassaAluksiOikeaMaaraLounaita() {
        assertEquals(0,kassa.edullisiaLounaitaMyyty() + kassa.maukkaitaLounaitaMyyty());
    }
    
    @Test
    public void kateisostoToimiiOikeinEdullisilleKunRahaaTarpeeksi() {
        int vaihtoraha = kassa.syoEdullisesti(500);
        
        assertEquals(100240, kassa.kassassaRahaa());
        assertEquals(260, vaihtoraha);
        assertEquals(1, kassa.edullisiaLounaitaMyyty());
    }
    
    @Test
    public void kateisostoToimiiOikeinEdullisilleKunRahaaEiTarpeeksi() {
        int vaihtoraha = kassa.syoEdullisesti(230);
        
        assertEquals(100000, kassa.kassassaRahaa());
        assertEquals(230, vaihtoraha);
        assertEquals(0, kassa.edullisiaLounaitaMyyty());
    }
    
    @Test
    public void kateisostoToimiiOikeinMaukkailleKunRahaaTarpeeksi() {
        int vaihtoraha = kassa.syoMaukkaasti(500);
        
        assertEquals(100400, kassa.kassassaRahaa());
        assertEquals(100, vaihtoraha);
        assertEquals(1, kassa.maukkaitaLounaitaMyyty());
    }
    
    @Test
    public void kateisostoToimiiOikeinMaukkailleKunRahaaEiTarpeeksi() {
        int vaihtoraha = kassa.syoMaukkaasti(230);
        
        assertEquals(100000, kassa.kassassaRahaa());
        assertEquals(230, vaihtoraha);
        assertEquals(0, kassa.maukkaitaLounaitaMyyty());
    }
    
    @Test
    public void korttiostoToimiiOikeinEdullisilleKunRahaaTarpeeksi() {
        assertTrue(kassa.syoEdullisesti(kortti));
        assertEquals(160, kortti.saldo());
        assertEquals(1, kassa.edullisiaLounaitaMyyty());
        assertEquals(100000, kassa.kassassaRahaa());
    }
    
    @Test
    public void korttiostoToimiiOikeinEdullisilleKunRahaaEiTarpeeksi() {
        kassa.syoEdullisesti(kortti);
        assertFalse(kassa.syoEdullisesti(kortti));
        assertEquals(160, kortti.saldo());
        assertEquals(1, kassa.edullisiaLounaitaMyyty());
        assertEquals(100000, kassa.kassassaRahaa());
    }
    
    @Test
    public void korttiostoToimiiOikeinMaukkailleKunRahaaTarpeeksi() {
        assertTrue(kassa.syoMaukkaasti(kortti));
        assertEquals(0, kortti.saldo());
        assertEquals(1, kassa.maukkaitaLounaitaMyyty());
        assertEquals(100000, kassa.kassassaRahaa());
    }
    
    @Test
    public void korttiostoToimiiOikeinMaukkailleKunRahaaEiTarpeeksi() {
        kassa.syoMaukkaasti(kortti);
        assertFalse(kassa.syoMaukkaasti(kortti));
        assertEquals(0, kortti.saldo());
        assertEquals(1, kassa.maukkaitaLounaitaMyyty());
        assertEquals(100000, kassa.kassassaRahaa());
    }
    
    @Test
    public void kortinLataaminenToimiiOikeinJosSummaOnEpanegatiivinen() {
        kassa.lataaRahaaKortille(kortti, 500);
        assertEquals(900, kortti.saldo());
        assertEquals(100500, kassa.kassassaRahaa());
    }
    
    @Test
    public void kortinLataaminenToimiiOikeinJosSummaOnNegatiivinen() {
        kassa.lataaRahaaKortille(kortti, -100);
        assertEquals(400, kortti.saldo());
        assertEquals(100000, kassa.kassassaRahaa());
    }
}
