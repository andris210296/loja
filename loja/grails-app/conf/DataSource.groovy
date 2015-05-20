dataSource {
    pooled = true
    driverClassName = "com.mysql.jdbc.Driver"
    dialect = "org.hibernate.dialect.MySQL5InnoDBDialect"
}
hibernate {
    cache.use_second_level_cache = true
    cache.use_query_cache = true
    cache.provider_class = 'net.sf.ehcache.hibernate.EhCacheProvider'
}
// environment specific settings
environments {
    development {
        dataSource {
            dbCreate = "update" // one of 'create', 'create-drop','update'
            url = "jdbc:mysql://localhost/loja"
            username = "root"
            password = "sof38"
        }
        hibernate {
            show_sql = true
        }
    }
    test {
        dataSource {
            dbCreate = "update" // one of 'create', 'create-drop','update'
            url = "jdbc:mysql://localhost/loja"
            username = "root"
            password = "sof38"
        }
    }
    production {
        dataSource {
            dbCreate = "update" // one of 'create', 'create-drop','update'
            url = "jdbc:mysql://localhost/loja"
            username = "root"
            password = "sof38"
        }
    }
}