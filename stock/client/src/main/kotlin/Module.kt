import org.koin.dsl.module

import client.Client
import getter.Getter

val appModule = module {
    single { Client() }
    single { Getter() }
}
