using System;
using System.Text;
using System.Linq;
using System.Collections.Generic;
using System.Text.RegularExpressions;

/// @author Tapio Saarnio
/// @version 30.11.2018
/// Ohjelma palauttaa käyttäjän haluaman asteikon
/// <summary>
/// Ohjelma kysyy käyttäjältä asteikkoa jonka hän haluaa tietää,
/// kokoaa ja tulostaa sen. Jos käyttäjän syöte on väärin,
/// ohjelma tulostaa "Syötä asteikko joka on olemassa"
/// Tyhjästä syötteestä tulostetaan "Kiitos ohjelman käytöstä!" ja 
/// ohjelma päättyy.
/// </summary>
public class ScaleFinder
{
    private static readonly string[,] kaikkinuotit2D ={{ "C", "C" },       //Kaikki olemassa olevat sävelet taulukossa sen mukaan onko ne alennettuja vai ylennettyjä
                                  { "C#","Db" },
                                  { "D", "D"  },
                                  { "D#", "Eb" },
                                  { "E",  "E" },
                                  { "F", "F" },
                                  { "F#", "Gb" },
                                  { "G", "G" },
                                  { "G#", "Ab" },
                                  { "A", "A" },
                                  { "A#", "Bb"},
                                  { "B", "B" },
                                  { "C", "C" },
                                  { "C#","Db" },
                                  { "D", "D"  },
                                  { "D#", "Eb" },
                                  { "E",  "E" },
                                  { "F", "F" },
                                  { "F#", "Gb" },
                                  { "G", "G" },
                                  { "G#", "Ab" },
                                  { "A", "A" },
                                  { "A#", "Bb"},
                                  { "B", "B" },
                                   };


    private static readonly char[] erottimet = { ' ', '-' };
    private static readonly string[] kaikkinuotit1D = { "C", "C#", "Db", "D", "D#", "Eb", "E", "F", "F#", "Gb", "G", "G#", "Ab", "A", "A#", "Bb", "B" }; //jotta voidaan käyttää hyväksi SisaltaakoTaulukko aliohjelmaa
    /// <summary>
    ///Kysytään käyttäjältä minkä asteikon hän haluaa tietää. Tarkistetaan onko käyttäjän antama syöte oikein, eli onko sellainen ohjelman käsittelemä
    ///asteikko minkä käyttäjä syöttää olemassa. Sen jälkeen selvitetään onko asteikko duuri vai molli ja kummalle (vasen tai oikea, 0 tai 1) sarakkeelle asteikko laitetaan rakentumaan.
    ///Kutsutaan aliohjelmaa ekasavel jonka perusteella saadaan tieto mistä kohdasta asteikko laitetaan rakentumaan. Lopuksi tulostetaan asteikko Duuri/MolliAsteikko aliohjelmalla.
    /// </summary>
    public static void Main()
    {

        bool jatketaanko = true;
        while (jatketaanko)  //Ohjelma toistaa itseään kunnes käyttäjä antaa tyhjän merkkijonon
        {  
            Console.Write("Syötä haluamasi asteikko (Esim: C duuri)>");
            string asteikko = Console.ReadLine();
            if (asteikko == "" || asteikko == null)
            {
                jatketaanko = false;
                Console.WriteLine("Kiitos ohjelman käytöstä!");
                break;
            }
            
            bool onkosyotejarkeva = TarkistaSyote(asteikko);
            if (!onkosyotejarkeva)
            {
                string tuloste = ("Syötä olemassa oleva asteikko");
                Console.WriteLine(tuloste);
                continue;
                    
            }

            int sarake = 0; //sarake kertoo mennäänkö asteikon rakentamisessa vasenta vai oikeaa puolta matriisissa alaspäin.
            int ekasavel = 0; //ekasavel kertoo mikä on asteikon ensimmäinen nuotti.

            if (asteikko.Contains("duuri") || asteikko.Contains("Duuri"))
            {
                string[] duuriylennykset = { "C", "D", "A", "E", "B", "F#" };  //Kaikki duuriasteikot jotka sisältävät ylennyksiä
                string[] duurialennnukset = { "F", "Bb", "Eb", "Ab", "Db", "Gb" }; //Kaikki duuriasteikot jotka sisältävät alennuksia
                if (SisaltaakoTaulukko(asteikko, duuriylennykset))
                {
                    sarake = 0;
                }
                if (SisaltaakoTaulukko(asteikko, duurialennnukset))
                {
                    sarake = 1;
                }

                ekasavel = EkaNuotti(asteikko, sarake);
                Duuriasteikko(ekasavel, sarake);

            }

            if (asteikko.Contains("molli") || asteikko.Contains("Molli"))
            {
                string[] molliylennykset = { "E", "B", "F#", "C#", "G#", "D", "A", "D#" }; //Kaikki molliasteikot jotka sisältävät ylennyksiä
                string[] mollialennukset = { "D", "G", "C", "F", "Bb", "Eb" };       //Kaikki molliasteikot jotka sisältävät alennuksia
                if (SisaltaakoTaulukko(asteikko, molliylennykset))
                {
                    sarake = 0;
                }
                if (SisaltaakoTaulukko(asteikko, mollialennukset))
                {
                    sarake = 1;
                }

                ekasavel = EkaNuotti(asteikko, sarake);
                Molliasteikko(ekasavel, sarake);
            }
        }
    }


    /// <summary>
    /// Tarkistaa onko käyttäjä syöttänyt asteikon joka on olemassa ja vieläpä oikeassa syntaksissa
    /// </summary>
    /// <param name="asteikko">Käyttäjän syöte</param>
    /// <returns>
    /// true jos käyttäjän kysymä asteikko on olemassa, false jos ei
    /// </returns>
    /// <example>
    /// <pre name="test">
    /// TarkistaSyote("C duuri")===true;
    /// TarkistaSyote("G duuri")===true;
    /// TarkistaSyote("D duuri")===true;
    /// TarkistaSyote("A duuri")===true;
    /// TarkistaSyote("E duuri")===true;
    /// TarkistaSyote("B duuri")===true;
    /// TarkistaSyote("Gb duuri")===true;
    /// TarkistaSyote("F duuri")===true;
    /// TarkistaSyote("Bb duuri")===true;
    /// TarkistaSyote("Eb duuri")===true;
    /// TarkistaSyote("Ab duuri")===true;
    /// TarkistaSyote("Db duuri")===true;
    /// TarkistaSyote("F# duuri")===true;
    /// TarkistaSyote("A molli")===true;
    /// TarkistaSyote("E molli")===true;
    /// TarkistaSyote("B molli")===true;
    /// TarkistaSyote("F# molli")===true;
    /// TarkistaSyote("C# molli")===true;
    /// TarkistaSyote("G# molli")===true;
    /// TarkistaSyote("Eb molli")===true;
    /// TarkistaSyote("D# molli")===true;
    /// TarkistaSyote("X molli")===false;
    /// TarkistaSyote("C mlli")===false;
    /// TarkistaSyote("molli")===false;
    /// TarkistaSyote("Cmolli")===false;
    /// TarkistaSyote("D# molli")===true;
    /// </pre>
    /// </example>
    public static bool TarkistaSyote(string asteikko)
    {
        bool loytyikoasteikontyyppi = false;
        if (asteikko.Contains("duuri") || asteikko.Contains("Duuri"))
        {
            loytyikoasteikontyyppi = true;
        }

        if (asteikko.Contains("molli") || asteikko.Contains("Molli"))
        {
            loytyikoasteikontyyppi = true;
        }
        bool loytyikonuotti = false;
        for (int i = 0; i < 2; i++)
        {
            if(SisaltaakoTaulukko(asteikko, kaikkinuotit1D))
            {
                loytyikonuotti = true;
            }
        }

        if (loytyikoasteikontyyppi && loytyikonuotti) 
        {
            return true;
        }
        else return false;
        
    }


    /// <summary>
    /// Etsitään mikä on käyttäjän haluaman asteikon ensimmäisen nuotin rivi kaikkisävelet-matriisissa
    /// </summary>
    /// <param name="asteikko">Käyttäjän syöte</param>
    /// <param name="sarake">kaikkinuotit2d sarake riippuen siitä käyttääkö asteikko ylennyksiä vai alennuksia</param>
    /// <returns>
    /// Käyttäjän haluaman asteikon ensimmäisen nuotin rivi
    /// </returns>
    ///<example>
    /// <pre name="test">
    /// EkaNuotti("C duuri", 0)===0;
    /// EkaNuotti("D duuri", 0)===2;
    /// EkaNuotti("A duuri", 0)===9;
    /// EkaNuotti("E duuri", 0)===4;
    /// EkaNuotti("B duuri", 0)===11;
    /// EkaNuotti("F# duuri", 0)===6;
    /// EkaNuotti("G duuri", 0)===7;
    /// EkaNuotti("F duuri", 1)===5;
    /// EkaNuotti("Bb duuri", 1)===10;
    /// EkaNuotti("Eb duuri", 1)===3;
    /// EkaNuotti("Ab duuri", 1)===8
    /// EkaNuotti("Db duuri", 1)===1;
    /// EkaNuotti("Gb duuri", 1)===6;
    /// EkaNuotti("A molli", 0)===9;
    /// EkaNuotti("E molli", 0)===4;
    /// EkaNuotti("B molli", 0)===11;
    /// EkaNuotti("F# molli", 0)===6;
    /// EkaNuotti("C# molli", 0)===1;
    /// EkaNuotti("G# molli", 0)===8;
    /// EkaNuotti("D molli", 1)===2;
    /// EkaNuotti("G molli", 1)===7;
    /// EkaNuotti("C molli", 1)===0;
    /// EkaNuotti("F molli", 1)===5;
    /// EkaNuotti("Bb molli", 1)===10;
    /// EkaNuotti("Eb molli", 1)===3;
    /// EkaNuotti("D# molli", 0)===3;
    /// </pre>
    /// </example>
    public static int EkaNuotti(string asteikko, int sarake)
    {
            string[] asteikkosplitattuna = Splittaa(erottimet, asteikko);

            int ekasavel = 0;
            for (int i = 0; i <= 23; i++)
            {
                if (asteikkosplitattuna[0].Equals(kaikkinuotit2D[i, sarake]))
                {
                    ekasavel = i;
                    break;
                }
                
            }
            return ekasavel;

    }


    /// <summary>
    /// Kokoaa ja tulostaa duuriasteikon
    /// </summary>
    /// <param name="ekasavel">Asteikon ensimmäinen sävel</param>
    /// <param name="sarake">kaikkinuotit2d sarake riippuen siitä käyttääkö asteikko ylennyksiä vai alennuksia</param>
    public static void Duuriasteikko(int ekasavel, int sarake)
    {


            Console.Write("{0} duuri on: {1} ", kaikkinuotit2D[ekasavel, sarake], kaikkinuotit2D[ekasavel, sarake]);
            Console.Write(" {0}", kaikkinuotit2D[ekasavel + 2, sarake]);
            Console.Write(" {0}", kaikkinuotit2D[ekasavel + 2 + 2, sarake]);
            Console.Write(" {0}", kaikkinuotit2D[ekasavel + 2 + 2 + 1, sarake]);
            Console.Write(" {0}", kaikkinuotit2D[ekasavel + 2 + 2 + 1 + 2, sarake]);
            Console.Write(" {0}", kaikkinuotit2D[ekasavel + 2 + 2 + 1 + 2 + 2, sarake]);
            Console.Write(" {0}", kaikkinuotit2D[ekasavel + 2 + 2 + 1 + 2 + 2 + 2, sarake]);
            Console.WriteLine();

    }


    /// <summary>
    /// Kokoaa ja tulostaa molliasteikon
    /// </summary>
    /// <param name="ekasavel">Asteikon ensimmäinen sävel</param>
    /// <param name="sarake">kaikkinuotit2d sarake riippuen siitä käyttääkö asteikko ylennyksiä vai alennuksia</param>
    public static void Molliasteikko(int ekasavel, int sarake)
    {


            Console.Write("{0} molli on: {1} ", kaikkinuotit2D[ekasavel, sarake], kaikkinuotit2D[ekasavel, sarake]);
            Console.Write(" {0}", kaikkinuotit2D[ekasavel + 2, sarake]);
            Console.Write(" {0}", kaikkinuotit2D[ekasavel + 2 + 1, sarake]);
            Console.Write(" {0}", kaikkinuotit2D[ekasavel + 2 + 1 + 2, sarake]);
            Console.Write(" {0}", kaikkinuotit2D[ekasavel + 2 + 1 + 2 + 2, sarake]);
            Console.Write(" {0}", kaikkinuotit2D[ekasavel + 2 + 1 + 2 + 2 + 1, sarake]);
            Console.Write(" {0}", kaikkinuotit2D[ekasavel + 2 + 1 + 2 + 2 + 1 + 2, sarake]);
            Console.WriteLine();


     }


        /// <summary>
        /// Tekee merkkijonotaulukon jotta EkaNuotti voi verrata ekaa indeksiä Taulukon kanssa.
        /// </summary>
        /// <param name="erottimet">Merkit joiden perusteella Split metodi katkaisee</param>
        /// <param name="asteikko">Käyttäjän syöte</param>
        /// <returns>
        /// Käyttäjän syöte paloiteltuna
        /// </returns>
        public static string[] Splittaa(char[] erottimet, string asteikko)
        {
            string[] asteikkosplitattuna = asteikko.Split(erottimet);
            return asteikkosplitattuna;
        }


        /// <summary>
        /// Tarkistaa sisältääkö  tutkittavat tapaukset sellaista tapausta kuin 
        /// käyttäjän syötteen ensimmäinen merkki/merkkijono ennen välilyöntiä
        /// </summary>
        /// <param name="asteikko">Käyttäjän syöte</param>
        /// <param name="tapaukset">Nuotteja joista johonkin uuden string taulukon ensimmäisen indeksin on viitattava jotta voidaan palauttaa true</param>
        /// <returns>
        /// true jos joku string [] tapaukset tapauksista on sama kuin asteikkosplitattuna[0], false jos ei
        /// </returns>
        public static bool SisaltaakoTaulukko(string asteikko, string[] tapaukset)
        {

            string[] asteikkosplitattuna = Splittaa(erottimet, asteikko);
            for (int i = 0; i < tapaukset.Length ; i++)
            {
                if (asteikkosplitattuna[0].Equals(tapaukset[i]))
                {
                    return true;

                }

            }

            return false;

        }
        
      
}


