"use client"

import type React from "react"

import { useState } from "react"
import { Calendar, Clock, Users, X } from "lucide-react"
import { Button } from "@/components/ui/button"
import { Input } from "@/components/ui/input"
import { Label } from "@/components/ui/label"
import { Textarea } from "@/components/ui/textarea"

interface BookingFormProps {
  tableId: number
  tableSize: number
  time: string
  isOpen: boolean
  onClose: () => void
  onSubmit: (data: any) => void
}

export default function BookingForm({ tableId, tableSize, time, isOpen, onClose, onSubmit }: BookingFormProps) {
  const [formData, setFormData] = useState({
    name: "",
    email: "",
    phone: "",
    guests: tableSize,
    specialRequests: "",
  })

  const handleChange = (e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>) => {
    const { name, value } = e.target
    setFormData((prev) => ({ ...prev, [name]: value }))
  }

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault()
    onSubmit(formData)
  }

  if (!isOpen) return null

  return (
    <div className="fixed inset-0 bg-[#051923]/50 flex items-center justify-center z-50 p-4">
      <div className="bg-white rounded-lg shadow-xl w-full max-w-md">
        <div className="flex items-center justify-between bg-[#006494] text-white p-4 rounded-t-lg">
          <h2 className="text-xl font-bold tracking-tight">Book Table #{tableId}</h2>
          <Button variant="ghost" size="icon" onClick={onClose} className="text-white hover:bg-[#00A6FB]/20">
            <X className="h-5 w-5" />
          </Button>
        </div>

        <div className="p-6">
          <div className="flex flex-wrap items-center gap-4 mb-6 text-sm bg-[#F8FBFF] p-4 rounded-lg border border-[#00A6FB]/20">
            <div className="flex items-center px-2 py-1 bg-white rounded-full shadow-sm border border-[#00A6FB]/20">
              <Clock className="h-4 w-4 mr-1 text-[#00A6FB]" />
              <span className="text-[#051923]">{time}</span>
            </div>
            <div className="flex items-center px-2 py-1 bg-white rounded-full shadow-sm border border-[#00A6FB]/20">
              <Users className="h-4 w-4 mr-1 text-[#0582CA]" />
              <span className="text-[#051923]">Table for {tableSize}</span>
            </div>
            <div className="flex items-center px-2 py-1 bg-white rounded-full shadow-sm border border-[#00A6FB]/20">
              <Calendar className="h-4 w-4 mr-1 text-[#006494]" />
              <span className="text-[#051923]">Today</span>
            </div>
          </div>

          <form onSubmit={handleSubmit} className="space-y-4">
            <div className="space-y-2">
              <Label htmlFor="name" className="text-[#051923]">
                Full Name
              </Label>
              <Input
                id="name"
                name="name"
                value={formData.name}
                onChange={handleChange}
                required
                placeholder="John Doe"
                className="border-[#00A6FB]/20 focus-visible:ring-[#00A6FB]"
              />
            </div>

            <div className="space-y-2">
              <Label htmlFor="email" className="text-[#051923]">
                Email
              </Label>
              <Input
                id="email"
                name="email"
                type="email"
                value={formData.email}
                onChange={handleChange}
                required
                placeholder="john@example.com"
                className="border-[#00A6FB]/20 focus-visible:ring-[#00A6FB]"
              />
            </div>

            <div className="space-y-2">
              <Label htmlFor="phone" className="text-[#051923]">
                Phone Number
              </Label>
              <Input
                id="phone"
                name="phone"
                value={formData.phone}
                onChange={handleChange}
                required
                placeholder="+1 (555) 123-4567"
                className="border-[#00A6FB]/20 focus-visible:ring-[#00A6FB]"
              />
            </div>

            <div className="space-y-2">
              <Label htmlFor="guests" className="text-[#051923]">
                Number of Guests
              </Label>
              <Input
                id="guests"
                name="guests"
                type="number"
                min="1"
                max={tableSize}
                value={formData.guests}
                onChange={handleChange}
                required
                className="border-[#00A6FB]/20 focus-visible:ring-[#00A6FB]"
              />
            </div>

            <div className="space-y-2">
              <Label htmlFor="specialRequests" className="text-[#051923]">
                Special Requests (Optional)
              </Label>
              <Textarea
                id="specialRequests"
                name="specialRequests"
                value={formData.specialRequests}
                onChange={handleChange}
                placeholder="Any dietary requirements or special occasions?"
                rows={3}
                className="border-[#00A6FB]/20 focus-visible:ring-[#00A6FB]"
              />
            </div>

            <div className="flex gap-3 pt-2">
              <Button
                type="button"
                variant="outline"
                onClick={onClose}
                className="flex-1 border-[#00A6FB]/20 text-[#051923] hover:bg-[#00A6FB]/10 hover:text-[#003554]"
              >
                Cancel
              </Button>
              <Button type="submit" className="flex-1 bg-[#006494] hover:bg-[#003554] text-white">
                Confirm Booking
              </Button>
            </div>
          </form>
        </div>
      </div>
    </div>
  )
}
