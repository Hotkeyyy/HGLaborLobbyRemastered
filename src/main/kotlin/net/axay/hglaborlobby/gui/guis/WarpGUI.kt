package net.axay.hglaborlobby.gui.guis

import net.axay.hglaborlobby.data.Warp
import net.axay.hglaborlobby.data.holder.WarpsHolder
import net.axay.hglaborlobby.database.DatabaseManager
import net.axay.kspigot.chat.KColors
import net.axay.kspigot.gui.GUIType
import net.axay.kspigot.gui.Slots
import net.axay.kspigot.gui.kSpigotGUI
import net.axay.kspigot.gui.openGUI
import net.axay.kspigot.items.itemStack
import net.axay.kspigot.items.name
import net.axay.kspigot.items.setMeta
import net.axay.kspigot.items.toLoreList
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

object WarpGUI {

    private val warpsGUI = kSpigotGUI(GUIType.THREE_BY_NINE) {

        page(1) {

            title = "${KColors.RED}"

            placeholder(Slots.Border, ItemStack(Material.PINK_STAINED_GLASS_PANE))

            val warpsCompound = createRectCompound<Warp>(

                Slots.RowTwoSlotTwo,
                Slots.RowTwoSlotEight,

                iconGenerator = {
                    itemStack(it.icon) {
                        setMeta {
                            name = "${KColors.CORAL}${it.name.capitalize()}"
                            lore = (it.description?.toLoreList(KColors.DARKAQUA)?.toMutableList() ?: ArrayList()).apply {
                                this += " "
                                this += "Klicke auf dieses Item, um dich zu diesem Warp zu teleportieren."
                                    .toLoreList(KColors.LIGHTSLATEGRAY, KColors.ITALIC)
                            }
                        }
                    }
                },

                onClick = { clickEvent, element ->

                    clickEvent.bukkitEvent.isCancelled = true

                    clickEvent.bukkitEvent.whoClicked.teleport(element.location)

                }

            )

            warpsCompound.addContent(WarpsHolder.instance.warps)

        }

    }

    fun enable() {

        MainGUI.addContent(MainGUI.MainGUICompoundElement(
            Material.COMPASS,
            "Warps",
            "Sehe alle Warps, die existieren.",
            onClick = { it.player.openGUI(warpsGUI) }
        ))

    }

}