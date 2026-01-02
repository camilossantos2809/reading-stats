export type Book = {
  id: number;
  isbn: string;
  name: string;
  author: string | null;
  pages: number | null;
  asin: string | null;
};

export type ReadingBook = {
  book: Book;
  currentPage: number;
};
