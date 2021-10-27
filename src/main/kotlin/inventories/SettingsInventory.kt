package inventories

import database.Util
import net.kyori.adventure.text.Component
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta

class SettingsInventory {
    fun createSettingsInventory(player: Player) {

        //Components
        val inventoryTitle: Component = Component.text("Settings")
        val backgroundDisplayName: Component = Component.empty()
        val closeDisplayName: Component = Component.text("Menü schließen")
        val increaseDisplayName: Component = Component.text("Rückstoß vergrößern")
        val decreaseDisplayName: Component = Component.text("Rückstoß verringern")
        val knockbackLevelDisplayName: Component =
            Component.text("Level").append(Component.text(Util().getKnockbackLevel(player).toString()))

        //Create Inventory
        val inventory = Bukkit.createInventory(player, 9, inventoryTitle)


        //Background Item
        val background = ItemStack(Material.GRAY_STAINED_GLASS_PANE)
        val backgroundItemMeta: ItemMeta = background.itemMeta
        backgroundItemMeta.displayName(backgroundDisplayName)
        background.itemMeta = backgroundItemMeta

        //Close Item
        val close = ItemStack(Material.BARRIER)
        val closeItemMeta: ItemMeta = close.itemMeta
        closeItemMeta.displayName(closeDisplayName)
        close.itemMeta = closeItemMeta

        //Increase Knockback Item
        val increase = ItemStack(Material.LIME_DYE)
        val increaseItemMeta: ItemMeta = increase.itemMeta
        increaseItemMeta.displayName(increaseDisplayName)
        increase.itemMeta = increaseItemMeta

        //Decrease Knockback Item
        val decrease = ItemStack(Material.RED_DYE)
        val decreaseItemMeta: ItemMeta = decrease.itemMeta
        decreaseItemMeta.displayName(decreaseDisplayName)
        decrease.itemMeta = decreaseItemMeta


        //Knockback Level Icon
        val level: Int = KickstickKotlin.knockbackMap[player] ?: 1 // Getting Level
        val knockbackLevel = ItemStack(Material.BLAZE_ROD, level) //Getting ItemStack with the level as amount
        val knockbackLevelItemMeta = knockbackLevel.itemMeta
        knockbackLevelItemMeta.displayName(knockbackLevelDisplayName)
        knockbackLevel.itemMeta = knockbackLevelItemMeta
        knockbackLevel.addUnsafeEnchantment(Enchantment.KNOCKBACK, level)


        for (i in 0..inventory.size) {
            inventory.setItem(i,background)
        }

        inventory.setItem(8, close)
        inventory.setItem(1, decrease)
        inventory.setItem(3, knockbackLevel)
        inventory.setItem(5, increase)

        player.openInventory(inventory)
    }
}
