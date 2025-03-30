import { BackButton } from "@/components/back-buttom";
import { BookCard } from "@/components/book-card";
import { NewReadingProgressForm } from "./new-reading-progress-form";
import { Book } from "@/types";
import { DeleteProgressButton } from "./delete-progress-button";

interface ReadingProgress {
  book: Book;
  progress: {
    id: string;
    dateRead: string;
    progressPrevious: number;
    progress: number;
    pagesRead: number;
  }[];
}

type HistoryProps = {
  params: Promise<{
    bookId: string;
  }>;
};

export default async function History({ params }: HistoryProps) {
  const _params = await params;
  const bookId = _params.bookId;
  const progress = await fetch(
    `http://localhost:8080/books/${bookId}/reading-progress`
  );
  const progressData: ReadingProgress = await progress.json();

  return (
    <div className="flex flex-col min-h-screen p-8 sm:p-12 space-y-8">
      <BackButton label="Back to Home" path={`/`} />
      <div className="max-w-2xl">
        <BookCard
          book={{
            ...progressData.book,
            pagesRead: 120,
            status: "reading",
            rating: 4,
          }}
        />
      </div>
      <NewReadingProgressForm bookId={bookId} />
      <div className="max-w-2xl space-y-4">
        <h2 className="text-xl font-bold">Previous Progress</h2>
        <ul className="space-y-2">
          {progressData.progress.map((entry, index) => (
            <li
              key={index}
              className="p-5 border border-gray-700 rounded-lg bg-gray-900 dark:bg-gray-800 shadow-sm hover:shadow-lg transition-shadow duration-200"
            >
              <div className="flex flex-col gap-3">
                <div className="flex justify-between items-center">
                  <p className="text-xs font-medium text-gray-400 dark:text-gray-500 uppercase tracking-wide">
                    {entry.dateRead}
                  </p>
                  <DeleteProgressButton progressId={entry.id} />
                </div>
                {/* Main Content */}
                <div className="flex justify-between items-center gap-4">
                  {/* Left: Book Info */}
                  <div className="flex flex-col gap-1.5">
                    <div className="flex items-center gap-2 text-sm text-gray-300 dark:text-gray-400">
                      <span>Last Page: {entry.progress ?? "N/A"}</span>
                      <span>-</span>
                      <span>Pages Read: {entry.pagesRead ?? "N/A"}</span>
                    </div>
                  </div>
                  {/* Right: Progress */}
                  <div className="flex flex-col items-end gap-1.5">
                    <span className="text-sm font-medium text-gray-200 dark:text-gray-300">
                      {`${Math.round(
                        (entry.progress / (progressData.book.pages ?? 0)) * 100
                      )}%`}
                    </span>
                    <Progress
                      value={
                        (entry.progress / (progressData.book.pages ?? 0)) * 100
                      }
                    />
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
