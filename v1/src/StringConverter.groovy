class StringConverter {
    String add(...args){
        return args.join()
    }
    String reciprocal(...args){
        return args.collect { it.reverse() }.join()
    }
    String emptyOrNull(...args){
        def r=args.join()
        return r==null || r.isEmpty()
    }
    String makeLowCase(...args){
        def r=args.join()
        r=r.toLowerCase()
        return r
    }
    String makeUpperCase(...args){
        def r=args.join()
        r=r.toUpperCase()
        return r
    }
}
