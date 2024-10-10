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
        dataSource.url = "jdbc:mysql://localhost:3306/looca?serverTimezone=America/Sao_Paulo"
        dataSource.username = "root"
        dataSource.password = "#Gf38851238863"

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

    fun inserir(): Boolean {
        val rede = loocaInserir.capturarDados()
        if (rede != null) {
            return try {
                val qtdLinhasAfetadas = jdbcTemplate.update(
                    """
                INSERT INTO monitoramento_rede (bytes_enviados, bytes_recebidos, pacotes_enviados, pacotes_recebidos, bytes_enviados_mb, bytes_recebidos_mb)
                VALUES (?, ?, ?, ?, ?, ?)
            """.trimIndent(),
                    rede.bytesEnviados,
                    rede.bytesRecebidos,
                    rede.pacotesEnviados,
                    rede.pacotesRecebidos,
                    rede.bytesEnviados / (1024 * 1024),
                    rede.bytesRecebidos / (1024 * 1024)
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
