"use client";

import { useState } from "react";
import { BackButton } from "@/components/back-buttom";
import { BookCard } from "@/components/book-card";
// import { Progress } from "@/components/progress";

interface ReadingProgress {
  date: string;
  lastPage: number;
  totalPages: number;
  percentage: number;
  pagesRead: number;
}

interface Book {
  id: number;
  name: string;
  author: string;
  pages: number;
}

const mockBook: Book = {
  id: 1,
  name: "The Pragmatic Programmer",
  author: "Dave Thomas",
  pages: 320,
};

// Mocked reading progress entries
const mockProgressEntries: ReadingProgress[] = [
  {
    date: "2023-01-01",
    lastPage: 50,
    totalPages: 320,
    percentage: 15,
    pagesRead: 50,
  },
  {
    date: "2023-01-05",
    lastPage: 100,
    totalPages: 320,
    percentage: 30,
    pagesRead: 100,
  },
  {
    date: "2023-01-10",
    lastPage: 150,
    totalPages: 320,
    percentage: 45,
    pagesRead: 150,
  },
  {
    date: "2023-01-15",
    lastPage: 200,
    totalPages: 320,
    percentage: 60,
    pagesRead: 200,
  },
  {
    date: "2023-01-20",
    lastPage: 250,
    totalPages: 320,
    percentage: 75,
    pagesRead: 250,
  },
  {
    date: "2023-01-25",
    lastPage: 300,
    totalPages: 320,
    percentage: 90,
    pagesRead: 300,
  },
  {
    date: "2023-01-30",
    lastPage: 320,
    totalPages: 320,
    percentage: 100,
    pagesRead: 320,
  },
  {
    date: "2023-02-01",
    lastPage: 0,
    totalPages: 320,
    percentage: 0,
    pagesRead: 0,
  },
  {
    date: "2023-02-05",
    lastPage: 0,
    totalPages: 320,
    percentage: 0,
    pagesRead: 0,
  },
  {
    date: "2023-02-10",
    lastPage: 0,
    totalPages: 320,
    percentage: 0,
    pagesRead: 0,
  },
];

export default function History() {
  const [date, setDate] = useState(new Date().toISOString().split("T")[0]); // Current date
  const [lastPage, setLastPage] = useState<number | null>(null);
  const [percentage, setPercentage] = useState<number | null>(null);

  const handleSaveProgress = () => {
    if (lastPage !== null || percentage !== null) {
      // Reset form fields
      setDate(new Date().toISOString().split("T")[0]);
      setLastPage(null);
      setPercentage(null);
    }
  };

  return (
    <div className="flex flex-col min-h-screen p-8 sm:p-12 space-y-8">
      <BackButton label="Back to Home" path={`/`} />
      <div className="max-w-2xl">
        <BookCard
          book={{
            id: 1,
            isbn: "1544512279",
            name: "Can't Hurt Me: Master Your Mind and Defy the Odds",
            author: "David Goggins",
            pages: 357,
            pagesRead: 120,
            status: "reading",
            rating: 4,
          }}
        />
      </div>
      {/* Progress Form */}
      <div className="max-w-2xl p-6 border rounded-lg space-y-4">
        <h2 className="text-xl font-bold">Update Reading Progress</h2>
        <div className="space-y-4">
          <div>
            <label htmlFor="date" className="block text-sm font-medium">
              Date
            </label>
            <input
              type="date"
              id="date"
              value={date}
              onChange={(e) => setDate(e.target.value)}
              className="w-full p-2 border rounded-lg"
            />
          </div>
          <div>
            <label htmlFor="lastPage" className="block text-sm font-medium">
              Last Page Read
            </label>
            <input
              type="number"
              id="lastPage"
              value={lastPage ?? ""}
              onChange={(e) => setLastPage(Number(e.target.value))}
              className="w-full p-2 border rounded-lg"
              placeholder="Enter last page read"
            />
          </div>
          <div>
            <label htmlFor="percentage" className="block text-sm font-medium">
              Percentage Read
            </label>
            <input
              type="number"
              id="percentage"
              value={percentage ?? ""}
              onChange={(e) => setPercentage(Number(e.target.value))}
              className="w-full p-2 border rounded-lg"
              placeholder="Enter percentage read"
            />
          </div>
          <button
            onClick={handleSaveProgress}
            className="px-6 py-2 rounded-lg border transition-all duration-300 hover:shadow-lg hover:scale-[1.02] hover:border-slate-400 dark:hover:border-slate-600"
          >
            Save Progress
          </button>
        </div>
      </div>
      {/* Previous Progress Entries */}
      <div className="max-w-2xl space-y-4">
        <h2 className="text-xl font-bold">Previous Progress</h2>
        <ul className="space-y-2">
          {mockProgressEntries.map((entry, index) => (
            <li
              key={index}
              className="p-5 border border-gray-700 rounded-lg bg-gray-900 dark:bg-gray-800 shadow-sm hover:shadow-lg transition-shadow duration-200"
            >
              <div className="flex flex-col gap-3">
                {/* Date Header */}
                <p className="text-xs font-medium text-gray-400 dark:text-gray-500 uppercase tracking-wide">
                  {entry.date}
                </p>
                {/* Main Content */}
                <div className="flex justify-between items-center gap-4">
                  {/* Left: Book Info */}
                  <div className="flex flex-col gap-1.5">
                    <div className="flex items-center gap-2 text-sm text-gray-300 dark:text-gray-400">
                      <span>Pages Read: {entry.pagesRead ?? "N/A"}</span>
                      <span>/</span>
                      <span>Total Pages: {entry.totalPages ?? "N/A"}</span>
                    </div>
                    <div className="text-sm text-gray-400 dark:text-gray-500">
                      Last Page: {entry.lastPage ?? "N/A"}
                    </div>
                  </div>
                  {/* Right: Progress */}
                  <div className="flex flex-col items-end gap-1.5">
                    <span className="text-sm font-medium text-gray-200 dark:text-gray-300">
                      {entry.percentage
                        ? `${Math.round(entry.percentage)}%`
                        : "0%"}
                    </span>
                    <Progress value={entry.percentage ?? 0} />
                  </div>
                </div>
              </div>
            </li>
          ))}
        </ul>
      </div>
    </div>
  );
}

interface ProgressProps {
  value: number;
  max?: number;
}

export function Progress({ value, max = 100 }: ProgressProps) {
  const clampedValue = Math.min(Math.max(value, 0), max);
  const percentage = (clampedValue / max) * 100;

  return (
    <div className="w-28 h-2.5 bg-gray-700 dark:bg-gray-600 rounded-full overflow-hidden">
      <div
        className="h-full bg-gradient-to-r from-blue-500 to-blue-700 dark:from-blue-400 dark:to-blue-600 transition-all duration-300 ease-in-out"
        style={{ width: `${percentage}%` }}
        aria-label={`Progress: ${Math.round(percentage)}%`}
      />
    </div>
  );
}
