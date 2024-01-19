class Calculator {
    def add(...args){
        return args.inject { acc, value -> acc + value }
    }
    def sub(...args){
        return args.inject { acc, value -> acc - value }
    }
    def multi(...args){
        return args.inject { acc, value -> acc * value }
    }
    def div(...args){
        if (args.any { it == 0 }) {
            throw new ArithmeticException("Nie można dzielić przez zero.")
        }
        return args.inject { acc, value -> acc.toDouble() / value.toDouble() }
    }
}
