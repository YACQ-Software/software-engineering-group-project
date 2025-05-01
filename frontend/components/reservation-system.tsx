"use client"

import { useState } from "react"
import { Clock, Users } from "lucide-react"
import { cn } from "@/lib/utils"
import TableLayout from "@/components/table-layout"
import TimeSelector from "@/components/time-selector"
import BookingForm from "@/components/booking-form"

// Sample data for tables
const tables = [
  { id: 1, size: 2, position: { x: 10, y: 10 }, shape: "circle" },
  { id: 2, size: 4, position: { x: 30, y: 10 }, shape: "rectangle" },
  { id: 3, size: 6, position: { x: 50, y: 10 }, shape: "rectangle" },
  { id: 4, size: 2, position: { x: 70, y: 10 }, shape: "circle" },
  { id: 5, size: 8, position: { x: 10, y: 30 }, shape: "rectangle" },
  { id: 6, size: 4, position: { x: 30, y: 30 }, shape: "rectangle" },
  { id: 7, size: 2, position: { x: 50, y: 30 }, shape: "circle" },
  { id: 8, size: 10, position: { x: 70, y: 30 }, shape: "rectangle" },
  { id: 9, size: 2, position: { x: 10, y: 50 }, shape: "circle" },
  { id: 10, size: 4, position: { x: 30, y: 50 }, shape: "rectangle" },
  { id: 11, size: 6, position: { x: 50, y: 50 }, shape: "rectangle" },
  { id: 12, size: 15, position: { x: 70, y: 50 }, shape: "rectangle" },
  { id: 13, size: 2, position: { x: 10, y: 70 }, shape: "circle" },
  { id: 14, size: 1, position: { x: 30, y: 70 }, shape: "circle" },
  { id: 15, size: 12, position: { x: 50, y: 70 }, shape: "rectangle" },
]

// Sample bookings data
const sampleBookings = {
  "12:00": [1, 3, 5],
  "13:00": [2, 4, 7, 9],
  "14:00": [6, 8],
  "15:00": [10, 12],
  "16:00": [11, 13],
  "17:00": [14, 15],
  "18:00": [1, 2, 3],
  "19:00": [4, 5, 6, 7],
  "20:00": [8, 9, 10],
  "21:00": [11, 12, 13],
  "22:00": [14, 15],
}

const timeSlots = ["12:00", "13:00", "14:00", "15:00", "16:00", "17:00", "18:00", "19:00", "20:00", "21:00", "22:00"]

export default function ReservationSystem() {
  const [selectedTime, setSelectedTime] = useState("18:00")
  const [selectedTable, setSelectedTable] = useState<number | null>(null)
  const [bookingModalOpen, setBookingModalOpen] = useState(false)

  // Get booked tables for the selected time
  const bookedTables = sampleBookings[selectedTime] || []

  const handleTableClick = (tableId: number) => {
    if (!bookedTables.includes(tableId)) {
      setSelectedTable(tableId)
      setBookingModalOpen(true)
    }
  }

  const handleBookingSubmit = (data: any) => {
    // Here you would handle the booking submission
    console.log("Booking submitted:", { table: selectedTable, time: selectedTime, ...data })
    setBookingModalOpen(false)
    setSelectedTable(null)
  }

  return (
    <div className="flex flex-col min-h-screen bg-white">
      <header className="bg-[#006494] text-white p-4 shadow-md">
        <div className="container mx-auto">
          <h1 className="text-2xl font-bold">Restaurant Reservation System</h1>
        </div>
      </header>

      <div className="container mx-auto p-4 flex-1 flex flex-col md:flex-row gap-6">
        <div className="md:w-1/4 bg-white p-4 rounded-lg shadow-sm border border-[#00A6FB]/20">
          <h2 className="text-xl font-semibold text-[#003554] mb-4 flex items-center">
            <Clock className="mr-2 h-5 w-5" /> Select Time
          </h2>
          <TimeSelector timeSlots={timeSlots} selectedTime={selectedTime} onSelectTime={setSelectedTime} />

          <div className="mt-8">
            <h2 className="text-xl font-semibold text-[#003554] mb-4 flex items-center">
              <Users className="mr-2 h-5 w-5" /> Table Sizes
            </h2>
            <div className="space-y-2">
              {[...new Set(tables.map((t) => t.size))]
                .sort((a, b) => a - b)
                .map((size) => (
                  <div key={size} className="flex items-center">
                    <div
                      className={cn(
                        "w-4 h-4 rounded-full mr-2",
                        size <= 2
                          ? "bg-[#00A6FB]"
                          : size <= 6
                            ? "bg-[#0582CA]"
                            : size <= 10
                              ? "bg-[#006494]"
                              : "bg-[#003554]",
                      )}
                    ></div>
                    <span className="text-[#051923]">
                      {size} {size === 1 ? "person" : "people"}
                    </span>
                  </div>
                ))}
            </div>
          </div>
        </div>

        <div className="md:w-3/4 bg-white p-6 rounded-xl shadow-sm border border-[#00A6FB]/20">
          <h2 className="text-xl font-semibold text-[#003554] mb-4">Table Layout - {selectedTime}</h2>
          <TableLayout tables={tables} bookedTables={bookedTables} onTableClick={handleTableClick} />
        </div>
      </div>

      {bookingModalOpen && selectedTable && (
        <BookingForm
          tableId={selectedTable}
          tableSize={tables.find((t) => t.id === selectedTable)?.size || 0}
          time={selectedTime}
          isOpen={bookingModalOpen}
          onClose={() => setBookingModalOpen(false)}
          onSubmit={handleBookingSubmit}
        />
      )}
    </div>
  )
}
