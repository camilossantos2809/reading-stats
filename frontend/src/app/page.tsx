import Link from "next/link";
import { GoalBook } from "@/types";
import { BookCardWrapper } from "./books/book-card-wrapper";

export default async function Home() {
  const readingBooks: GoalBook[] = [
    {
      id: 12,
      isbn: "8535930442",
      name: "A Caverna",
      author: "José Saramago",
      pages: 376,
      pagesRead: 80,
      status: "reading",
      rating: 0,
    },
    {
      isbn: "978-8535907445",
      name: "Declínio e queda do império romano",
      author: "Edward Gibbon",
      pages: 583,
      pagesRead: 308,
      id: 13,
      rating: 0,
      status: "reading",
    },
    // {
    //   isbn: "978-8520942611",
    //   name: "O Vermelho e o Negro",
    //   author: "Sthendal",
    //   pages: 588,
    //   pagesRead: 500,
    //   id: 8,
    //   rating: 5,
    //   status: "completed",
    // },
    // {
    //   isbn: "9788535929423",
    //   name: "O Som e a Fúria",
    //   author: "William Faulkner",
    //   pages: 412,
    //   pagesRead: 180,
    //   id: 6,
    //   rating: 4,
    //   status: "reading",
    // },
  ];

  return (
    <div className="grid grid-rows-[20px_1fr_20px] items-center justify-items-center min-h-screen p-8 pb-20 gap-16 sm:p-20 font-[family-name:var(--font-balthazar-sans)]">
      <main className="flex flex-col gap-8 row-start-2 items-center sm:items-start w-full">
        <div className="flex flex-col gap-4">
          <h1 className="text-4xl font-bold">Reading Stats</h1>
          <p className="text-lg">Keep track of your reading stats and goals</p>
        </div>
        <div className="grid grid-cols-1 sm:grid-cols-3 gap-6 mb-12">
          <Link
            href="/books"
            className="p-6 border rounded-lg transition-all duration-300 hover:shadow-lg hover:scale-[1.02] hover:border-slate-400 dark:hover:border-slate-600 group"
          >
            <h2 className="text-2xl font-bold mb-2">Books</h2>
            <p className="text-gray-400 group-hover:text-foreground transition-colors duration-300">
              Browse and manage your book collection
            </p>
          </Link>
          <Link
            href="/goals"
            className="p-6 border rounded-lg transition-all duration-300 hover:shadow-lg hover:scale-[1.02] hover:border-slate-400 dark:hover:border-slate-600 group"
          >
            <h2 className="text-2xl font-bold mb-2">Goals</h2>
            <p className="text-gray-400 group-hover:text-foreground transition-colors duration-300">
              Set and track your reading goals
            </p>
          </Link>
        </div>
        <div className="w-full">
          <h2 className="text-3xl font-bold mb-8">Currently Reading</h2>
          <div className="grid grid-cols-1 lg:grid-cols-3 gap-6">
            {readingBooks?.length > 0 ? (
              readingBooks.map((book) => (
                <BookCardWrapper key={book.isbn} book={book} />
              ))
            ) : (
              <div className="col-span-full p-6 border rounded-lg text-center">
                <p className="text-gray-400">No books currently being read</p>
                <a
                  href="/books"
                  className="inline-block mt-4 px-6 py-2 rounded-lg border transition-all duration-300 hover:shadow-lg hover:scale-[1.02] hover:border-slate-400 dark:hover:border-slate-600"
                >
                  Find a book to read
                </a>
              </div>
            )}
          </div>
        </div>
        <div className="w-full">
          <h2 className="text-3xl font-bold mb-8">Stats Current Year</h2>
          <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-6">
            <div className="p-6 border rounded-lg transition-all duration-300 hover:shadow-lg hover:border-slate-400 dark:hover:border-slate-600">
              <p className="text-gray-400">Books Read</p>
              <p className="text-4xl font-bold">0</p>
            </div>
            <div className="p-6 border rounded-lg transition-all duration-300 hover:shadow-lg hover:border-slate-400 dark:hover:border-slate-600">
              <p className="text-gray-400">Pages Read</p>
              <p className="text-4xl font-bold">0</p>
            </div>
            <div className="p-6 border rounded-lg transition-all duration-300 hover:shadow-lg hover:border-slate-400 dark:hover:border-slate-600">
              <p className="text-gray-400">Average Pages Read Per Day</p>
              <p className="text-4xl font-bold">0</p>
            </div>
            <div className="p-6 border rounded-lg transition-all duration-300 hover:shadow-lg hover:border-slate-400 dark:hover:border-slate-600">
              <p className="text-gray-400">Average Pages Read Per Month</p>
              <p className="text-4xl font-bold">0</p>
            </div>
          </div>
        </div>
      </main>
      <footer className="row-start-3 flex gap-6 flex-wrap items-center justify-center"></footer>
    </div>
  );
}
