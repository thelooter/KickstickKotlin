package database

import eu.tuxcraft.databaseprovider.DatabaseProvider
import org.bukkit.entity.Player
import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.SQLException

class Util {
    fun getKnockbackLevel(player: Player): Int {
        try {
            val connection: Connection = DatabaseProvider.getConnection()

            val preparedStatement =
                connection.prepareStatement("SELECT value FROM player_properties WHERE uuid = ? AND flag = ?")
            preparedStatement.setObject(1, player.uniqueId)
            preparedStatement.setString(2, "kickstick_knockback")
            val resultSet: ResultSet = preparedStatement.executeQuery()
            return if (resultSet.next()) {
                resultSet.getString("value").toInt()
            } else {
                1
            }
        } catch (exception: SQLException) {
            exception.printStackTrace()
        }
        return 1
    }

    fun setKnockbackLevel(player: Player, level: Int) {
        try {
            val connection: Connection = DatabaseProvider.getConnection()

            val insertStatement: PreparedStatement =
                connection.prepareStatement("INSERT INTO player_properties (uuid, flag, value) VALUES (?,?,?)")
            val updateStatement: PreparedStatement =
                connection.prepareStatement("UPDATE player_properties SET value =? WHERE uuid=? AND flag=?")

            updateStatement.setString(1, level.toString())
            updateStatement.setObject(2, player.uniqueId)
            updateStatement.setString(3, "kickstick_knockback")

            if (updateStatement.executeUpdate() == 1) {
                return
            } else {
                insertStatement.setObject(1, player.uniqueId)
                insertStatement.setString(2, "kickstick_knockback")
                insertStatement.setString(3, level.toString())
                insertStatement.executeUpdate()
            }
        } catch (exception: SQLException) {
            exception.printStackTrace()
        }
    }

    fun hasDatabaseEntry(player: Player): Boolean {
        try {
            val connection = DatabaseProvider.getConnection()
            val checkStatement =
                connection.prepareStatement("SELECT exists(SELECT 1 FROM player_properties where uuid=? AND flag = ?)")

            checkStatement.setObject(1, player.uniqueId)
            checkStatement.setString(2, "kickstick_knockback")
            val resultSet: ResultSet = checkStatement.executeQuery()
            if (resultSet.next()) {
                return resultSet.getBoolean(1)
            }
        } catch (exception: SQLException) {
            exception.printStackTrace()

        }
        return false

    }
}






