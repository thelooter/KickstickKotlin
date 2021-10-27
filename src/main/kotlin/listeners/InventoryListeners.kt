package listeners

import KickstickKotlin
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer
import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent

class InventoryListeners : Listener {
    @EventHandler
    fun onInventoryClick(event: InventoryClickEvent) {

        //Player Variable
        val player = event.whoClicked as Player

        //Inventory Variables
        val inventory = event.inventory
        val inventoryView = event.view


        //Utility Variables
        val currentItem = event.currentItem
        val blazeRod = inventory.getItem(3) ?: throw NullPointerException("KnockbackLevel Item is null")
        val title = PlainTextComponentSerializer.plainText().serialize(inventoryView.title())
        if (title != "Settings") {
            return
        }
        if (currentItem == null || currentItem.type == Material.AIR) {
            return
        }
        var amount = blazeRod.amount

        //Close Inventory
        if (currentItem.type == Material.BARRIER) {
            player.closeInventory()
            event.isCancelled = true
        }

        //Decrease Knockback Level and ItemStack amount if Amount is greater than one
        if (currentItem.type == Material.RED_DYE) {
            amount = blazeRod.amount
            if (amount <= 1) {
                event.isCancelled = true
                return
            }
            val kickStickLevelComponent: Component = Component.text("Level: ").append(Component.text(amount - 1))
            blazeRod.amount = amount - 1
            val blazeRodItemMeta = blazeRod.itemMeta
            blazeRodItemMeta.displayName(kickStickLevelComponent)
            blazeRod.itemMeta = blazeRodItemMeta
            blazeRod.addUnsafeEnchantment(Enchantment.KNOCKBACK, blazeRod.amount)
            KickstickKotlin.knockbackMap.replace(event.whoClicked as Player, blazeRod.amount)
            event.isCancelled = true
        }

        //Increase Knockback Level and ItemStack amount if Amount is greater than one
        if (currentItem.type == Material.LIME_DYE) {
            if (amount >= 10) {
                event.isCancelled = true
                return
            }
            val kickStickLevelComponent: Component = Component.text("Level: ").append(Component.text(amount - 1))

            blazeRod.amount = amount + 1
            val blazeRodItemMeta = blazeRod.itemMeta
            blazeRodItemMeta.displayName(kickStickLevelComponent)
            blazeRod.itemMeta = blazeRodItemMeta
            blazeRod.addUnsafeEnchantment(Enchantment.KNOCKBACK, blazeRod.amount)
            KickstickKotlin.knockbackMap.replace(event.whoClicked as Player, blazeRod.amount)
            event.isCancelled = true
        }

        //Stop Click if the Item is the Blaze Rod which represents the Knockback Level
        if (currentItem.type == Material.BLAZE_ROD) {
            if (event.isLeftClick) {
                if (amount >= 10) {
                    event.isCancelled = true
                    return
                }
                val kickStickLevelComponent: Component = Component.text("Level: ").append(Component.text(amount - 1))

                blazeRod.amount = amount + 1
                val blazeRodItemMeta = blazeRod.itemMeta
                blazeRodItemMeta.displayName(kickStickLevelComponent)
                blazeRod.itemMeta = blazeRodItemMeta
                currentItem.addUnsafeEnchantment(Enchantment.KNOCKBACK, currentItem.amount)
                KickstickKotlin.knockbackMap.replace(event.whoClicked as Player, blazeRod.amount)
                event.isCancelled = true
            }
            if (event.isRightClick) {
                if (amount <= 1) {
                    event.isCancelled = true
                    return
                }
                val kickStickLevelComponent: Component = Component.text("Level: ").append(Component.text(amount - 1))

                blazeRod.amount = amount - 1
                val blazeRodItemMeta = blazeRod.itemMeta
                blazeRodItemMeta.displayName(kickStickLevelComponent)
                blazeRod.itemMeta = blazeRodItemMeta
                currentItem.addUnsafeEnchantment(Enchantment.KNOCKBACK, currentItem.amount)
                KickstickKotlin.knockbackMap.replace(event.whoClicked as Player, blazeRod.amount)
                event.isCancelled = true
            }
        }

        //Stop Click if the Item is the Gray Glass Pane representing the Background
        if (currentItem.type == Material.GRAY_STAINED_GLASS_PANE) {
            event.isCancelled = true
        }
    }

}