class MainApp {
  static void main(String[] args) {
    def calc = new Calculator();
    println("dodawanie "+calc.add(2,3,1))
    println("odejmowanie "+calc.sub(5,1,1))
    println("mnożenie "+calc.multi(1,2,2))
    println("dzielenie  "+calc.div(2,2,2))
    //println("dzielenie z zerem "+calc.div(2,0,1))

    def strings = new StringConverter()
    println(strings.add("dodany","tekst","123"))
    println(strings.reciprocal("KamilSlimak","123"))
    println(strings.emptyOrNull("nie jestem pusty"))
    println(strings.emptyOrNull(" "," "))
    println(strings.emptyOrNull())
    println(strings.makeLowCase("Tu","Byly","Duze","Litery"))
    println(strings.makeUpperCase("tu","byly","male","litery"))
  }
}