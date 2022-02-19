package util

import java.awt.Desktop
import java.net.URI
import java.util.*

// Open a given link in the desktop browser.
// Code from: https://stackoverflow.com/a/68426773/816416
fun openInBrowser(uri: URI) {

    val osName by lazy(LazyThreadSafetyMode.NONE) { System.getProperty("os.name").lowercase(Locale.getDefault()) }
    val desktop = Desktop.getDesktop()
    when {
        Desktop.isDesktopSupported() && desktop.isSupported(Desktop.Action.BROWSE) -> desktop.browse(uri)
        "mac" in osName -> Runtime.getRuntime().exec("open $uri")
        "nix" in osName || "nux" in osName -> Runtime.getRuntime().exec("xdg-open $uri")
        else -> throw RuntimeException("Unable to open $uri")
    }

}