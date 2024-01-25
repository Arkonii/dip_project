



class Main {
    static void main(String[] args) {
        //def car=new Car()
        def colors=["czerwony","zielony","niebieski"] as String[]
        colors.putAt(0,"czarny")//zmiana pierwszej pozycji w tablicy na "czarny"
        println colors[0]//wypisanie pierwszej pozycji z tablicy
    }
}
