package com.example

final class Main {
    static void main(String[] args) {
        def rpn = new RPN()
        def result = rpn.main("2 7 + 3 / 14 3 - 4 * + 2 /")
        println("result in main : "+result)
    }
}
