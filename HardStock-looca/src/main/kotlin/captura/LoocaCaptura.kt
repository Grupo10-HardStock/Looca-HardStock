package captura

import com.github.britooo.looca.api.core.Looca

data class RedeDados(
    val bytesEnviados: Long,
    val bytesRecebidos: Long,
    val pacotesEnviados: Long,
    val pacotesRecebidos: Long
)

class LoocaCaptura (){

    val looca = Looca()
    val interfaces = looca.rede.grupoDeInterfaces.interfaces

    fun capturarDados(): RedeDados? {
        // Iterando sobre as interfaces de rede e imprimindo os detalhes
        for (redeDaVez in interfaces) {
            println(redeDaVez)
        }

        // Filtrando a interface de conexão principal (modificado para incluir wlan0)
        val interfaceDeConexaoPrincipal = interfaces.filter {
            it.nome.lowercase().contains("wlan") // Aqui, estamos filtrando por "wlan" para capturar wlan0
        }

        // Verificando se a interface principal foi encontrada
        if (interfaceDeConexaoPrincipal.isNotEmpty()) {
            val rede = interfaceDeConexaoPrincipal[0]

            // Acessando os atributos da interface de conexão principal
            return RedeDados(
                bytesEnviados = rede.bytesEnviados,
                bytesRecebidos = rede.bytesRecebidos,
                pacotesEnviados = rede.pacotesEnviados,
                pacotesRecebidos = rede.pacotesRecebidos
            )
        } else {
            println("Nenhuma interface de conexão principal encontrada.")
            return null
        }
    }



}