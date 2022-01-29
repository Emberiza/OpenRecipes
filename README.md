# OpenRecipes
semestrální práce AK7MT

Aplikace zaměřená na prohlížení si receptů a jejich ingrediencí z API.
Požadavky:
☑ zdrojové kódy přístupné v nějakém verzovacím systému (GIT) - vše zde

☑ vlastní ikona aplikace, vlastní splashscreen - obojí custom made o mé Máti, která zrovna dostala od firmy tablet :)

☑ více propojených obrazovek, funkční navigační stack (přechody mezi obrazovkami) - využita doporučená architektura MVVM 

☑ vstup od uživatele (zadávání dat uživatelem) - použit SearchView k vyhledávání specifických receptů, nebo také podle ingrediencí 

☑ komunikace s libovolným REST API - implementováno za pomoci Retrofit, obrázky z API za pomoci Glide

X ukládání dat do persistentní paměti - nebyl jsem schopen implementovat Room (což mi přišlo jako nejlepší varianta) databázi, ať jsem nad tím strávil několik dní.
Nejdříve jsem přemýšlel o ukládání specifických receptů, poté alespoň o Cache v případě, že by vypadlo internetové připojení, ale obě varianty se ukázali být příliš náročné.
Databázové soubory, kterými jsem toto chtěl dělat jsem v projektu nechal, kdyby jste se na ně chtěl podívat. K ničemu nejsou připojené, tudíž ničemu nevadí.
Doufám, že toto neinvaliduje celý projekt, samozřejmě očekávám horší známku.

NOTES:
Největším problémem ve vytvoření aplikace byl Kotlin a Retrofit. 
Nad Retrofitem jsem strávil moc času, naštěstí nakonec za pomoci MVVM architektury se začali vypisovat data tak, jak jsem chtěl.
Kotlin je ovšem zlo. Na bakaláři v Jihlavě jsme dělali v Javě, tudíž jsem se ho musel učit od nuly, a není to velmi příjemný jazyk.

OBČASNĚ (respektive poslední dobou často) se objeví bug, kdy při buildu soubor RecipeListActivity.kt hlásí špatnou referenci na RecipeRecyclerView. 
Mám pocit, že je to z důvodu, že adresáře projektu jsem nazíval s velkým prvním písmenem. 
Bug se vyřeší použitím funkce "Clean Project" pod záložkou "Build".
