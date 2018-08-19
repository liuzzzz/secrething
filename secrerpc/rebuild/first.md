#Registry
    
    zookeeper/???



#Consumer--Registry(subscribe)

##Proxy
    jdk/cglib
##Remote
    netty
         protocol

#Producer--Registry(publish)
##Remote
    netty
        protocol
        
##Invoke
    jdk/cglib
    
##### register/discover
`provider -(register)-> registry` 
    
               notify

`consumer -(subscribe) -> registry`


#####RPC    
`consumer->proxy->remote-> provider`

                               |       
                               v
`consumer     <-- remote<- dd- invoke`