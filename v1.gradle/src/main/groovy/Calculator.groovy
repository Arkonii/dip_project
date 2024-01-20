class Calculator {
    def add(...args){
        return args.inject { acc, value -> acc + value }
    }
    def sub(...args){
        return args.inject { acc, value ->
            if(value<0){
                acc + value
            }else
            acc - value }
    }
    def multi(...args){
        return args.inject { acc, value -> acc * value }
    }
    def div(...args){


        def result = args.drop(1).inject(args[0]) { acc, value ->
            if (value == 0) {
                throw new ArithmeticException("Nie można dzielić przez zero.")
            }
            acc / value
        }

        return result
    }
}
