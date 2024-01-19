class StringConverter {
    String add(...args){
        return args.join()
    }
    String reciprocal(...args){
        return args.collect { it.reverse() }.join()
    }
    Boolean emptyOrNull(...args){
        def r=args.join()
        if(r==null || r.isEmpty()){
            return true
        }
        return false
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
