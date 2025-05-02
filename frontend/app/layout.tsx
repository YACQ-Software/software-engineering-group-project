// app/layout.tsx
import { ReactNode } from "react";
import Script from "next/script";
import { ThemeProvider } from "@/components/theme-provider";

export default function RootLayout({ children }: { children: ReactNode }) {
  return (
    <html lang="en">
      <head>
        <Script
          src="https://cdn.tailwindcss.com"
          strategy="beforeInteractive"
        />
      </head>
      <body>
          <ThemeProvider attribute="class" defaultTheme="light" enableSystem>
            {children}
          </ThemeProvider>
      </body>
    </html>
  );
}