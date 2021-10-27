package commands

import inventories.SettingsInventory
import org.bukkit.entity.Player

class KickstickSettingsSubcommand {
    fun executeSettingsSubCommand(player: Player) {
        SettingsInventory().createSettingsInventory(player)
    }
}
