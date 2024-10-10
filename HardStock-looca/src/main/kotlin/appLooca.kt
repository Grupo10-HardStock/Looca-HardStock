import captura.LoocaCaptura
import repositorio.LoocaRepositorio
import java.lang.Thread.sleep

fun main() {
    val loocaConfigurar = LoocaRepositorio()
    loocaConfigurar.configurar()

    try {
        loocaConfigurar.criarTabela()
        println("Tabela criada com sucesso.")
    } catch (e: Exception) {
        println("Erro ao criar tabela: ${e.message}")
    }

    while (true) {
        try {
            if (loocaConfigurar.inserir()) {
                println("Dados inseridos com sucesso.")
            } else {
                println("Nenhum dado inserido.")
            }
            sleep(10000)
        } catch (e: Exception) {
            println("Erro ao inserir dados: ${e.message}")
        }
    }
}
