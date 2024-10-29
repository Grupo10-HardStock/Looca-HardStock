import captura.LoocaCaptura
import repositorio.LoocaRepositorio
import java.lang.Thread.sleep

fun main() {
    val loocaConfigurar = LoocaRepositorio()
    loocaConfigurar.configurar()

    while (true) {
        try {
            if (loocaConfigurar.inserirBytesEnviados()) {
                loocaConfigurar.inserirBytesRecebidos()
                println("Dados inseridos com sucesso.")
            } else {
                println("Nenhum dado inserido.")
            }
            sleep(20000)
        } catch (e: Exception) {
            println("Erro ao inserir dados: ${e.message}")
        }
    }
}
