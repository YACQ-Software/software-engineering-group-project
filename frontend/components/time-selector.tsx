"use client"

import { cn } from "@/lib/utils"
import { Button } from "@/components/ui/button"
import { Clock } from "lucide-react"

interface TimeSelectorProps {
  timeSlots: string[]
  selectedTime: string
  onSelectTime: (time: string) => void
}

export default function TimeSelector({ timeSlots, selectedTime, onSelectTime }: TimeSelectorProps) {
  return (
    <div className="space-y-2">
      {timeSlots.map((time) => (
        <Button
          key={time}
          variant="outline"
          className={cn(
            "w-full justify-start text-left font-normal rounded-lg transition-all",
            selectedTime === time
              ? "bg-[#00A6FB] text-white border-[#00A6FB]"
              : "hover:bg-[#00A6FB]/10 hover:text-[#003554] border-[#00A6FB]/20 text-[#051923]",
          )}
          onClick={() => onSelectTime(time)}
        >
          <Clock className="h-3.5 w-3.5 mr-2 opacity-70" />
          {time}
        </Button>
      ))}
    </div>
  )
}
