# Period
1) Работа с периодами
Теперь мы знаем достаточно, чтобы сделать что-то веселое с датами! Наш зоопарк выполняет мероприятия по обогащению животных, чтобы дать им что-то интересное. Главный зоопарк решил поменять игрушки каждый месяц. Эта система будет работать в течение трех месяцев, чтобы увидеть, как она работает.

public static void main(String[] args) { 
 LocalDate start = LocalDate.of(2015, Month.JANUARY, 1);
  LocalDate end = LocalDate.of(2015, Month.MARCH, 30); 
 performAnimalEnrichment(start, end);
}
private static void performAnimalEnrichment(LocalDate start, LocalDate end) {
  LocalDate upTo = start; 
 while (upTo.isBefore(end)) {          // check if still before end   
System.out.println("give new toy: " + upTo); 
  upTo  = upTo.plusMonths(1);                    // add a month  
}}
Эта Код работает нормально. Это добавляет месяц к дате, пока не достигнет конечной даты. Проблема в том, что этот метод нельзя использовать повторно. Наш зоопарк хочет попробовать разные графики, чтобы увидеть, какой из них работает лучше всего.
Преобразование в long
LocalDateи LocalDateTimeесть метод для преобразования их в longэквиваленты по отношению к 1970 году. Что особенного в 1970 году? Именно это UNIX начал использовать для стандартов даты, поэтому Java использовала его повторно. И не волнуйтесь - вам не нужно запоминать имена для экзамена.
•	LocalDateимеет toEpochDay()количество дней с 1 января 1970 года.
•	LocalDateTimeимеет toEpochTime(), что число секунд с 1 января 1970 года.
•	LocalTimeне имеет эпохального метода. Поскольку оно представляет собой время, которое наступает в любую дату, сравнивать его в 1970 году не имеет смысла. Несмотря на то, что экзамен делает вид, что часовых поясов не существует, вам может быть интересно, наступило ли это специальное время 1 января 1970 года зона. Ответ - да. Это специальное время относится к 1 января 1970 года по Гринвичу (среднее время по Гринвичу). Гринвич находится в Англии, а GMT не участвует в переходе на летнее время. Это делает его хорошим ориентиром. (Опять же, вам не нужно знать о GMT для экзамена.)
К счастью, в Java есть Periodкласс, который мы можем передать. Этот код делает то же самое, что и предыдущий пример:

public static void main(String[] args) {  
LocalDate start = LocalDate.of(2015, Month.JANUARY, 1);
  LocalDate end = LocalDate.of(2015, Month.MARCH, 30); 
 Period period = Period.ofMonths(1);               // create a period
  performAnimalEnrichment(start, end, period);
}
private static void performAnimalEnrichment(LocalDate start, LocalDate end, 
 Period period) {               // uses the generic period 
 LocalDate upTo = start;  
while (upTo.isBefore(end)) {  
  System.out.println("give new toy: " + upTo); 
   upTo = upTo.plus(period);     // adds the period 
 }}
Метод может добавить произвольный промежуток времени, который передается. Это позволяет нам повторно использовать один и тот же метод для разных периодов времени, так как наш зоокейпер передумает.
Есть пять способов создать Periodкласс:

Period annually = Period.ofYears(1);               // every 1 year
Period quarterly = Period.ofMonths(3);               // every 3 months
Period everyThreeWeeks = Period.ofWeeks(3);          // every 3 weeks
Period everyOtherDay = Period.ofDays(2);          // every 2 days
Period everyYearAndAWeek = Period.of(1, 0, 7);          // every year and 7 days
Там в один улов. Вы не можете связывать методы при создании Period. Следующий код выглядит как everyYearAndAWeekпример, но это не так. Используется только последний метод, потому что Periodметоды .ofXXX являются статическими.

Period wrong = Period.ofYears(1).ofWeeks(1);          // every week
Этот хитрый код действительно похож на написание следующего:
Period wrong = Period.ofYears(1);
wrong = Period.ofWeeks(7);
Это явно не то, что вы хотели! Вот почему of()метод позволяет нам передавать количество лет, месяцев и дней. Все они включены в один и тот же период. Вы получите предупреждение компилятора об этом. Предупреждения компилятора говорят о том, что что-то не так или подозрительно, не компилируя.
Вы, наверное, уже заметили, что Periodэто день или больше времени. Существует также Duration, который предназначен для меньших единиц времени. Для Duration, вы можете указать количество дней, часов, минут, секунд, или наносекунд. И да, вы могли бы провести 365 дней, чтобы сделать год, но вы действительно не должны - вот для чего Period. Durationне на экзамене, так как он работает примерно так же, как Period. Хорошо, что оно существует.
Последнее, что нужно знать, Periodэто то, с какими объектами это можно использовать. Давайте посмотрим на некоторый код:

3: LocalDate date = LocalDate.of(2015, 1, 20);
4: LocalTime time = LocalTime.of(6, 15);
5: LocalDateTime dateTime = LocalDateTime.of(date, time);
6: Period period = Period.ofMonths(1);
7: System.out.println(date.plus(period));          // 2015-02-20
8: System.out.println(dateTime.plus(period));          // 2015-02-20T06:15
9: System.out.println(time.plus(period));   // UnsupportedTemporalTypeException
Строки 7 и 8 работают как положено. Они добавляют месяц к 20 января 2015 года, что дает нам 20 февраля 2015 года. У первого есть только дата, а у второго - и дата, и время.
Строка 9 пытается добавить месяц к объекту, у которого есть только время. Это не сработает. Java выдает исключение и жалуется, что мы пытаемся использовать Unsupported unit: Months.
Как видите, вам придется обращать внимание на тип объектов даты и времени в каждом месте, где вы их видите.



2)Форматирование даты и времени 

классы даты и времени поддерживают множество методов для получения данных из них:

LocalDate date = LocalDate.of(2020, Month.JANUARY, 20);
System.out.println(date.getDayOfWeek());     // MONDAY
System.out.println(date.getMonth());          // JANUARY
System.out.println(date.getYear());          // 2020
System.out.println(date.getDayOfYear());     // 20
Мы могли бы использовать эту информацию для отображения информации о дате. Тем не менее, это будет больше работы, чем необходимо. Java предоставляет класс, призванный DateTimeFormatterпомочь нам. В отличие от LocalDateTimeкласса, DateTimeFormatterможет использоваться для форматирования любого типа даты и / или времени объекта. Какие изменения это формат. DateTimeFormatterнаходится в пакете java.time.format.

LocalDate date = LocalDate.of(2020, Month.JANUARY, 20);
LocalTime time = LocalTime.of(11, 12, 34);
LocalDateTime dateTime = LocalDateTime.of(date, time);
System.out.println(date.format(DateTimeFormatter.ISO_LOCAL_DATE));
System.out.println(time.format(DateTimeFormatter.ISO_LOCAL_TIME));
System.out.println(dateTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));

ISO это стандарт для дат. Вывод предыдущего кода выглядит следующим образом:
2020-01-20
11:12:34
2020-01-20T11:12:34
Это разумный способ общения компьютеров, но, вероятно, не то, как вы хотите выводить дату и время в своей программе. К счастью, есть несколько предопределенных форматов, которые более полезны:

DateTimeFormatter shortDateTime = 
 DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT);
System.out.println(shortDateTime.format(dateTime));     // 1/20/20
System.out.println(shortDateTime.format(date));      // 1/20/20
System.out.println(  
shortDateTime.format(time)); // UnsupportedTemporalTypeException

Здесь мы говорим, что нам нужен локализованный форматер в предопределенном коротком формате. Последняя строка выдает исключение, потому что время не может быть отформатировано как дата. format()Метод объявлен как на объекты и средства форматирования даты / времени объектов, что позволяет ссылаться на объекты в любом порядке. Следующие операторы печатают точно так же, как и предыдущий код:
DateTimeFormatter shortDateTime =  
DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT);
System.out.println(dateTime.format(shortDateTime));
System.out.println(date.format(shortDateTime));
System.out.println(time.format(shortDateTime));

В В этой книге мы изменим порядок заказов, чтобы вы привыкли видеть ее в обоих направлениях. В таблице 3.5 показаны допустимые и недопустимые методы локализованного форматирования.
Таблица 3.5 ofLocalized methods


DateTimeFormatter f = DateTimeFormatter._____(FormatStyle.SHORT);	Calling f.format(localDate)	Calling f.format(localDateTime)	Calling f.format(localTime)
ofLocalizedDate	Legal - показывает весь объект	Legal - показывает только часть даты	Выдает исключение времени выполнения
ofLocalizedDateTime	Выдает исключение времени выполнения	Legal - показывает весь объект	Выдает исключение времени выполнения
ofLocalizedTime	Выдает исключение времени выполнения	Legal - показывает только временную часть	Legal - показывает весь объект

На экзамене могут отображаться два предопределенных формата: SHORTи MEDIUM. Другие предопределенные форматы включают часовые пояса, которых нет на экзамене.
LocalDate date = LocalDate.of(2020, Month.JANUARY, 20);
LocalTime time = LocalTime.of(11, 12, 34);
LocalDateTime dateTime = LocalDateTime.of(date, time);
DateTimeFormatter shortF = DateTimeFormatter  
 .ofLocalizedDateTime(FormatStyle.SHORT);
DateTimeFormatter mediumF = DateTimeFormatter
  .ofLocalizedDateTime(FormatStyle.MEDIUM);
System.out.println(shortF.format(dateTime));     // 1/20/20 11:12 AM
System.out.println(mediumF.format(dateTime));     // Jan 20, 2020 11:12:34 AM
Перед мы смотрим на синтаксис, знаем, что вы не должны запоминать, что означают разные числа каждого символа. Максимум, что вам нужно сделать, это распознать дату и время.
1 MMMM
 Mпредставляет месяц. Чем больше у Mвас есть, тем более подробный вывод Java. Например, Mвыходы 1, MMвыходы 01, MMMвыходы января и MMMMвыходы января.
2 Dd
 dпредставляет дату в месяце. Как и в случае с месяцем, чем больше у dвас s, тем более подробный вывод Java. ddозначает включить ведущий ноль для однозначного месяца.
, Используйте , если вы хотите вывести запятую (это также появляется после года).
3 Yyyy
 yпредставляет год. yyвыводит двузначный год и yyyyвыводит четырехзначный год.
4 Hh
 hпредставляет час. Используйте hhдля включения начального нуля, если вы выводите однозначный час.
:  Используйте: если вы хотите вывести двоеточие.
5 Mm 
m представляет минуту.
Форматирование дат в Java 7 и ранее
Форматирование примерно эквивалентно «старому способу»; он просто использует другой класс.
DateTimeFormatter f = DateTimeFormatter.ofPattern("MMMM dd, yyyy, hh:mm");
System.out.println(dateTime.format(f));     // January 20, 2020, 11:12

	Старый способ	Новый способ (Java 8 и позже)
Форматирование времени	SimpleDateFormat sf = new SimpleDateFormat("hh:mm");
sf.format(jan3);	SimpleDateFormat sf = new SimpleDateFormat("hh:mm");
sf.format(jan3);

Давайте сделаем краткий обзор. Можете ли вы выяснить, какие из этих строк будут вызывать исключение?


4: DateTimeFormatter f = DateTimeFormatter.ofPattern("hh:mm");
5: f.format(dateTime);
6: f.format(date);
7: f.format(time);
Если вы получили этот вопрос на экзамене, подумайте, что обозначают символы. У нас есть hчас и mминута. Помните M(заглавными буквами) является месяц, а m(строчными) - минуты. Мы можем использовать этот форматер только с объектами, содержащими времена. Следовательно, строка 6 выдаст исключение.
Разбор дат и времени
Сейчас же что вы знаете, как преобразовать дату или время в форматированный String, вам будет легко конвертировать Stringв дату или время. Точно так же, как format()метод, parse()метод также принимает форматировщик. Если вы не укажете один, он использует значение по умолчанию для этого типа.

DateTimeFormatter f = DateTimeFormatter.ofPattern("MM dd yyyy");
LocalDate date = LocalDate.parse("01 02 2015", f);
LocalTime time = LocalTime.parse("11:22");
System.out.println(date);          // 2015-01-02
System.out.println(time);          // 11:22
Здесь мы показываем как пользовательский форматер, так и значение по умолчанию. Это не часто, но вам может понадобиться прочитать код, который выглядит следующим образом на экзамене. Синтаксический анализ согласуется с тем, что если что-то идет не так, Java выдает исключение времени выполнения. Это может быть формат, который не соответствует Stringанализируемой или недопустимой дате.

