namespace java com.linecorp.k8s.monitoring.sample.thrift

service GreetingService {
    Greeting hello(1:string name)
}

struct Greeting {
    1: i64 id,
    2: string greeting
}
