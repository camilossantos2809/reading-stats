export type Book = {
  id: number;
  isbn: string;
  name: string;
  author: string | null;
  pages: number | null;
};

export interface Goal {
  id: number;
  name: string;
  pagesTotal: number;
  pagesRead: number;
  booksCount: number | null;
  balance: number | null;
  speedIdeal: number | null;
  speedAverage: number | null;
  daysAboveGoal: number | null;
  daysBelowGoal: number | null;
  books: GoalBook[];
}

type BookStatus = "reading" | "to_read" | "completed";

interface GoalBook extends Book {
  pagesRead: number;
  status: BookStatus;
  rating: number | null;
}
