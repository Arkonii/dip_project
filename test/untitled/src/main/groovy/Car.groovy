

interface RunOrStop {
    def status
    void start()
    void stop()
}
class Car implements RunOrStop {
    def brand
    def model
    def year



    Car(brand, model, year) {
        this.brand = brand
        this.model = model
        this.year = year
    }

    def displayInfo() {
        println "Car: $year $brand $model"
    }
    String status=new String("stop")
    void start(){
        status="run"
    }
    void stop(){
        status="stop"
    }
}