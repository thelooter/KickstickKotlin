package listeners

import database.Util
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.TextComponent
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerInteractAtEntityEvent
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent
import java.util.*

class PlayerListener : Listener {


    //UUIDs of the Players who cannot be kicked with the Kickstick (nicknack65, thelooter2204, Tuxgamer, Tuxcraft32)
    private val forbiddenIDs: Set<UUID> = HashSet(
        listOf(
            UUID.fromString("d3b66f9b-9a37-455f-81b7-daea9746e3a1"),
            UUID.fromString("f3d64d31-7461-41c7-98ba-1399cb98d630"),
            UUID.fromString("08fbc97b-93cd-4f2a-9369-29e025136b08"),
            UUID.fromString("41cd5b66-d2ed-4933-aaff-89f27e2b63b6")
        )
    )

    @EventHandler(ignoreCancelled = true)
    fun onRightClick(event: PlayerInteractAtEntityEvent) {
        val entity = event.rightClicked as? Player ?: return
        val player = event.player
        val itemInHand = event.player.inventory.itemInMainHand

        //Check if Player has Item In Hand and check if it has ItemMeta
        if (!itemInHand.hasItemMeta()) {
            return
        }

        //If Item in hand has ItemMeta, storing it to a variable
        val itemMeta = itemInHand.itemMeta

        //Component containing the Display name of the Kickstick (Component required by the Paper API)
        val displayName: TextComponent = Component.text("Kickstick", NamedTextColor.GOLD)

        //Serializing the Display Name Component to a String
        val displayNameString = PlainTextComponentSerializer.plainText().serialize(displayName)
        var itemMetaDisplayNameString = ""

        //If the Item has a Display Name, serializing the Display Name given by the ItemMeta
        if (itemMeta.hasDisplayName()) {
            itemMetaDisplayNameString = PlainTextComponentSerializer.plainText().serialize(itemMeta.displayName()!!)
        }

        //Check if Display Name equals the Name given by the ItemMeta
        if (displayNameString != itemMetaDisplayNameString) {
            return
        }
        if (!player.hasPermission("tuxcraft.kickstick")) {
            return
        }

        //Check if the Player tries to kick a blacklisted Player
        if (forbiddenIDs.contains(entity.uniqueId)) {
            val kickParams = HashMap<String, String>()
            kickParams["player"] = entity.displayName().toString()
            val kickMessage: Component = Component.text("That's too bad", NamedTextColor.DARK_RED)
            player.kick(kickMessage)
            return
        }

        //Components containing the Kick Messages
        val targetKickMessage: Component =
            Component.text("Oops, you've been kicked", NamedTextColor.RED)
        val playerKickMessage: Component =
            Component.text("Your nasty, you kicked that player", NamedTextColor.GREEN)
        entity.kick(targetKickMessage)
        player.sendMessage(playerKickMessage)
    }

    @EventHandler(ignoreCancelled = true)
    fun onJoin(event: PlayerJoinEvent) {
        if (Util().hasDatabaseEntry(event.player)) {
            KickstickKotlin.knockbackMap[event.player] = Util().getKnockbackLevel(event.player)
        } else {
            KickstickKotlin.knockbackMap[event.player] = 1
        }
    }

    @EventHandler
    fun onLeave(event: PlayerQuitEvent) {
        Util().setKnockbackLevel(event.player, KickstickKotlin.knockbackMap[event.player] ?: 1)
        KickstickKotlin.knockbackMap.remove(event.player)
    }

}
