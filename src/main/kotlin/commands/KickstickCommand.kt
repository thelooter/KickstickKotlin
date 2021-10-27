package commands

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.Material
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.enchantments.Enchantment
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta

class KickstickCommand : CommandExecutor {

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {

        val player = sender as? Player ?: return false

        if (!player.hasPermission("tuxcraft.kickstick")) {
            player.sendMessage(Component.text("Du hast nicht die nötigen Rechte", NamedTextColor.RED))
            return true
        }

        if (args.size == 1) {
            if (args[0] != "settings") {
                player.sendMessage(Component.text("Das ist keine Valide Option"))
                return true
            }
            KickstickSettingsSubcommand().executeSettingsSubCommand(player)
        }

        if (args.size > 1) {
            player.sendMessage(Component.text("Das sind zu viele Operatoren", NamedTextColor.RED))
        }

        if (!KickstickKotlin.knockbackMap.containsKey(player)) {
            KickstickKotlin.knockbackMap[player] = 1
        }

        if (player.inventory.getItem(1) != null) {
            return true
        }

        val itemStack = ItemStack(Material.BLAZE_ROD,1)
        val itemMeta: ItemMeta = itemStack.itemMeta
        itemMeta.displayName(Component.text("Kickstick", NamedTextColor.GOLD))
        itemMeta.addEnchant(Enchantment.KNOCKBACK, KickstickKotlin.knockbackMap.get(player) ?: 1, true)
        itemStack.itemMeta = itemMeta

        player.inventory.addItem(itemStack)

        player.sendMessage(Component.text("Du hast den Kickstick erhalten", NamedTextColor.GOLD))
        return true
    }

}