package com.berlin.presentation

import com.berlin.presentation.io.Reader
import com.berlin.presentation.io.Viewer

class MainMenuUi(
    private val runner: List<UiRunner>,
    private val viewer: Viewer,
    private val reader: Reader
) : UiRunner {
    override val id: Int = 0
    override val label: String = "Main menu"

    override suspend fun start(
    ) {
        while (true) {
            showMenu()
            val input = reader.read()?.trim()
            if (input.equals("exit", ignoreCase = true)) {
                viewer.show("Goodbye!")
                break
            }
            handleSelection(input)
        }
    }

    private fun showMenu() {
        viewer.show("Welcome to weather clothes app!")
        viewer.show("please choose an options or type 'exit' to quit: ")
        runner.forEach { option ->
            viewer.show("${option.id} - ${option.label}")
        }
    }

    private suspend fun handleSelection(input: String?) {
        val selectedId = input?.toIntOrNull()
        val selectedRunner = runner.find { it.id == selectedId }

        if (selectedRunner == null) {
            viewer.show("Please choose a correct number!")
        } else {
            selectedRunner.start()
        }
    }
}