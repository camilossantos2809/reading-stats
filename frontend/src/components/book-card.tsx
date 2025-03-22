import Image from "next/image";
import { Progress } from "@/components/progress";
import { GoalBook } from "@/types";

interface BookCardProps {
  book: GoalBook;
  onPress?: () => void;
}

export function BookCard({ book, onPress }: BookCardProps) {
  const progress = Math.round((book.pagesRead / (book.pages ?? 0)) * 100);

  return (
    <div
      className="flex gap-6 p-6 border rounded-lg transition-all duration-300 hover:shadow-lg hover:border-slate-400 dark:hover:border-slate-600 cursor-pointer"
      onClick={onPress}
    >
      <div className="relative min-w-[125px] h-[180px] rounded-lg overflow-hidden">
        <Image
          alt={`${book.name} cover`}
          src={`https://covers.openlibrary.org/b/isbn/${book.isbn}-M.jpg`}
          fill
          className="object-cover"
        />
      </div>

      <div className="flex flex-col justify-between flex-1">
        <div className="space-y-2">
          <h3 className="text-xl font-bold line-clamp-2">{book.name}</h3>
          {book.author && <p className="text-gray-400">{book.author}</p>}
        </div>

        <div className="space-y-2">
          <div className="flex justify-between text-sm">
            <span>Progress</span>
            <span>{progress}%</span>
          </div>
          <Progress value={progress} />
          <p className="text-sm text-gray-400">
            {book.pagesRead} of {book.pages} pages
          </p>
        </div>
      </div>
    </div>
  );
}
