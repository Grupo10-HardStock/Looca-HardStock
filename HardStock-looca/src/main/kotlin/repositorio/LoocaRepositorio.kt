package repositorio

import captura.LoocaCaptura
import org.apache.commons.dbcp2.BasicDataSource
import org.springframework.jdbc.core.JdbcTemplate

class LoocaRepositorio {
    val loocaInserir = LoocaCaptura()
    lateinit var jdbcTemplate: JdbcTemplate

    fun configurar() {
        val dataSource = BasicDataSource()
        dataSource.driverClassName = "com.mysql.cj.jdbc.Driver"
        dataSource.url = "jdbc:mysql://34.205.36.110:3306/HardStock?serverTimezone=America/Sao_Paulo"
        dataSource.username = "root"
        dataSource.password = "urubu100"

        jdbcTemplate = JdbcTemplate(dataSource)
    }

    fun criarTabela() {
        jdbcTemplate.execute(
            """
        CREATE TABLE monitoramento_rede (
            id INT AUTO_INCREMENT PRIMARY KEY,
            data_hora TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
            bytes_enviados BIGINT NOT NULL,
            bytes_recebidos BIGINT NOT NULL,
            pacotes_enviados BIGINT NOT NULL,
            pacotes_recebidos BIGINT NOT NULL,
            bytes_enviados_mb DECIMAL(10, 2) NOT NULL,
            bytes_recebidos_mb DECIMAL(10, 2) NOT NULL
        )""".trimIndent()
        )
    }

    fun inserirBytesEnviados(): Boolean {
        val rede = loocaInserir.capturarDados()
        if (rede != null) {
            return try {
                val qtdLinhasAfetadas = jdbcTemplate.update(
                    """
                INSERT INTO Capturas (fkServidor, valor, fkComponente)
                VALUES (1, ?, (SELECT idComponente FROM Componentes WHERE nome = 'Bytes Enviados'))
                """.trimIndent(),
                    rede.bytesEnviados
                )
                qtdLinhasAfetadas > 0
            } catch (e: Exception) {
                println("Erro ao inserir dados: ${e.message}")
                false
            }
        } else {
            println("Nenhuma interface de conexão principal encontrada.")
            return false
        }
    }



    fun inserirBytesRecebidos(): Boolean {
        val rede = loocaInserir.capturarDados()
        if (rede != null) {
            return try {
                val qtdLinhasAfetadas = jdbcTemplate.update(
                    """
                INSERT INTO Capturas (fkServidor, valor, fkComponente)
                VALUES (1, ?, (SELECT idComponente FROM Componentes WHERE nome = 'Bytes Recebidos'))
                """.trimIndent(),
                    rede.bytesRecebidos
                )
                qtdLinhasAfetadas > 0
            } catch (e: Exception) {
                println("Erro ao inserir dados: ${e.message}")
                false
            }
        } else {
            println("Nenhuma interface de conexão principal encontrada.")
            return false
        }
    }



}
