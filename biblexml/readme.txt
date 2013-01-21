Новый универсальный стандарт хранения Библии: biblexml

<?xml version="1.0" encoding="UTF-8"?>
<biblexml>
	<descripion>
		<version language="ru">Библия. Русский Синодальный перевод</version>
	</descripion>
	<chapterviews>
		<chapter name="Глава"/>
		<psalom name="Псалом"/>
	</chapterviews>
	<booknames>
		<book="ПЕРВАЯ КНИГА МОИСЕЕВА: БЫТИЕ" chapters="50"/>
		<book="ВТОРАЯ КНИГА МОИСЕЕВА: ИСХОД" chapters="40"/>
		...
	</booknames>
	<booktext id="Gen">
		<chapter id="Gen.1">
			<verse id="Gen.1.1">В начале сотворил Бог небо и землю.</verse>
			<verse id="Gen.1.2">Земля же была безвидна и пуста, и тьма над бездною, и Дух Божий носился над водою.</verse>
			...
		</chapter>
		<chapter id="Gen.2">
			<verse id="Gen.2.1">Так совершены небо и земля и все воинство их.</verse>
			<verse id="Gen.2.2">И совершил Бог к седьмому дню дела Свои, которые Он делал, и почил в день седьмый от всех дел Своих, которые делал.</verse>
			...
		</chapter>
	</booktext>
	<booktext id="Exod">
		<chapter id="Exod.1">
			<verse id="Exod.1.1">Вот имена сынов Израилевых, которые вошли в Египет с Иаковом, вошли каждый с домом своим:</verse>
			<verse id="Exod.1.2">Рувим, Симеон, Левий и Иуда,</verse>
			...
		</chapter>
	</booktext>
</biblexml>

Описание: все атрибуты(идентификаторы для парсинга) книг, глав и стихов строго в английском обозначении, как международного языка.