"use client";

import { useState } from "react";
import { useRouter } from "next/navigation";
import { BackButton } from "@/components/back-buttom";
import { BookCard } from "@/components/book-card";

interface ReadingProgress {
  date: string;
  lastPage: number | null;
  percentage: number | null;
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

export default function History() {
  const [progressEntries, setProgressEntries] = useState<ReadingProgress[]>([]);
  const [date, setDate] = useState(new Date().toISOString().split("T")[0]); // Current date
  const [lastPage, setLastPage] = useState<number | null>(null);
  const [percentage, setPercentage] = useState<number | null>(null);

  const handleSaveProgress = () => {
    if (lastPage !== null || percentage !== null) {
      setProgressEntries((prev) => [...prev, { date, lastPage, percentage }]);
      // Reset form fields
      setDate(new Date().toISOString().split("T")[0]);
      setLastPage(null);
      setPercentage(null);
    }
  };

  return (
    <div className="flex flex-col min-h-screen p-8 sm:p-12 space-y-8">
      <BackButton label="Back to Goal" path={`/goals/${mockBook.id}`} />
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
        {progressEntries.length > 0 ? (
          <ul className="space-y-2">
            {progressEntries.map((entry, index) => (
              <li key={index} className="p-4 border rounded-lg">
                <p className="text-sm text-gray-400">{entry.date}</p>
                <p>
                  Last Page: {entry.lastPage ?? "N/A"} | Percentage:{" "}
                  {entry.percentage ?? "N/A"}%
                </p>
              </li>
            ))}
          </ul>
        ) : (
          <p className="text-gray-400">No progress entries yet.</p>
        )}
      </div>
    </div>
  );
}
