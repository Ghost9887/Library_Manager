/*
 Aplikácia bude obsahovať 3 agendy:

    Zoznam všetkých kníh s informáciami o ich názve, 
    autorovi a príznaku, či je kniha požičaná.
    Zoznam všetkých čitateľov s informáciami o mene, priezvisku, 
    čísle občianskeho preukazu a dátume narodenia.
    Zoznam výpožičiek a vrátení jednotlivých kníh čitateľom, 
    pričom sa aktualizuje príznak, či je kniha požičaná.

    Knižnica bude mať tri dátové tabuľky
        Zoznam kníh (stĺpce: ID knihy, Názov, Autor, príznak či je vypožičaná)
        Zoznam čitateľov (stĺpce: Číslo občianskeho preukazu, Meno, Priezvisko, Dátum narodenia)
        Zoznam výpožičiek a vrátení kníh – ktorý čitateľ si kedy požičal ktorú knihu a kedy ju vrátil
        Ako dátové úložisko môžete použiť textový súbor, XML, alebo ľubovoľnú databázu (SQLite, MySQL...) 
    Užívateľský interface – aplikácia musí obsahovať:
        Dve agendy - zoznam kníh, zoznam čitateľov s formulármi na pridanie a editáciu záznamu
        Funkcionalitu na vypožičanie a vrátenie knihy
        Do zoznam kníh naprogramujte funkciu, ktorá zobrazí čitateľa, ktorý má knihu požičanú
    Pred prezentáciou naplňte aplikáciu niekoľkými vzorovými údajmi
*/

public class Main {
  public static void main(String[] args) {
    Manager manager = new Manager();
    new Data(manager).readData();
    new GUI(manager).run();
  }
}
