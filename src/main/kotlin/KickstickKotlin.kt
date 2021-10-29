import commands.KickstickCommand
import listeners.InventoryListeners
import listeners.PlayerListener
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin

class KickstickKotlin : JavaPlugin() {

    companion object{
        var knockbackMap: HashMap<Player, Int> = HashMap()
        var instance: KickstickKotlin? = null
            private set
    }

    override fun onEnable() {

        instance = this

        server.pluginManager.registerEvents(InventoryListeners(), this)
        server.pluginManager.registerEvents(PlayerListener(),this)

        getCommand("kickstickkotlin")?.setExecutor(KickstickCommand())


    }
}