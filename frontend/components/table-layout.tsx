"use client"

import { cn } from "@/lib/utils"
import { Coffee, Users } from "lucide-react"

interface TableProps {
  tables: Array<{
    id: number
    size: number
    position: { x: number; y: number }
    shape: "circle" | "rectangle"
  }>
  bookedTables: number[]
  onTableClick: (tableId: number) => void
}

export default function TableLayout({ tables, bookedTables, onTableClick }: TableProps) {
  return (
    <div className="relative w-full h-[600px] bg-[#F8FBFF] rounded-xl overflow-hidden border border-[#00A6FB]/20 shadow-inner">
      {/* Restaurant layout background elements */}
      <div className="absolute top-0 left-0 w-full h-16 bg-[#00A6FB]/10 flex items-center justify-center backdrop-blur-sm">
        <Coffee className="h-8 w-8 text-[#0582CA] mr-2" />
        <span className="text-[#003554] font-semibold tracking-wide">Bar Area</span>
      </div>

      <div className="absolute bottom-0 left-0 w-full h-16 bg-[#00A6FB]/10 flex items-center justify-center backdrop-blur-sm">
        <span className="text-[#003554] font-semibold tracking-wide">Entrance</span>
      </div>

      {/* Tables */}
      {tables.map((table) => {
        const isBooked = bookedTables.includes(table.id)
        const xPercent = table.position.x
        const yPercent = table.position.y

        // Determine table size based on capacity
        const tableWidth = table.shape === "circle" ? table.size * 10 + 40 : table.size * 15 + 40
        const tableHeight = table.shape === "circle" ? table.size * 10 + 40 : 80

        // Determine color based on table size with the specified blues
        const tableColor = isBooked
          ? "bg-gray-200 border-gray-300"
          : table.size <= 2
            ? "bg-[#00A6FB] border-[#00A6FB] hover:bg-[#00A6FB]/90"
            : table.size <= 6
              ? "bg-[#0582CA] border-[#0582CA] hover:bg-[#0582CA]/90"
              : table.size <= 10
                ? "bg-[#006494] border-[#006494] hover:bg-[#006494]/90"
                : "bg-[#003554] border-[#003554] hover:bg-[#003554]/90"

        return (
          <div
            key={table.id}
            className={cn(
              "absolute flex items-center justify-center border-2 cursor-pointer transition-colors shadow-md",
              tableColor,
              table.shape === "circle" ? "rounded-full" : "rounded-lg",
              isBooked ? "cursor-not-allowed opacity-60" : "",
            )}
            style={{
              width: `${tableWidth}px`,
              height: `${tableHeight}px`,
              left: `calc(${xPercent}% - ${tableWidth / 2}px)`,
              top: `calc(${yPercent}% - ${tableHeight / 2}px)`,
            }}
            onClick={() => !isBooked && onTableClick(table.id)}
          >
            <div className="text-white font-bold flex flex-col items-center">
              <span>{table.id}</span>
              <div className="flex items-center mt-1">
                <Users className="h-4 w-4 mr-1" />
                <span>{table.size}</span>
              </div>
            </div>
          </div>
        )
      })}
    </div>
  )
}
