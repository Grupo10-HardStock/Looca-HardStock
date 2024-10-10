package app

import repositorio.LoocaRepositorio
import java.lang.Thread.sleep

open class Main {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val teste = LoocaRepositorio()
            teste.configurar()
            while (true) {
                try {
                    if (teste.inserir()) {
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

    }
}