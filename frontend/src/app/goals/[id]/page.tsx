import Link from "next/link";
import { Progress } from "@/components/progress";
import type { Goal } from "@/types";
import Image from "next/image";
import { StatusFilter } from "@/components/status-filter";
import { AddBookButton } from "@/components/add-book-button";
import { BackButton } from "@/components/back-buttom";

// Mock data for a single goal with books
const mockGoal: Goal = {
  id: 1,
  name: "2024 Reading Challenge",
  pagesTotal: 5000,
  pagesRead: 2150,
  booksCount: 12,
  balance: 150,
  speedIdeal: 25,
  speedAverage: 28,
  daysAboveGoal: 45,
  daysBelowGoal: 15,
  books: [
    {
      id: 1,
      isbn: "9780123456789",
      name: "The Pragmatic Programmer",
      author: "Dave Thomas",
      pages: 320,
      pagesRead: 158,
      status: "reading",
      rating: 4,
    },
    {
      id: 2,
      isbn: "9780987654321",
      name: "Clean Code",
      author: "Robert C. Martin",
      pages: 464,
      pagesRead: 464,
      status: "completed",
      rating: 5,
    },
    {
      id: 3,
      isbn: "9781449331818",
      name: "Learning JavaScript Design Patterns",
      author: "Addy Osmani",
      pages: 254,
      pagesRead: 0,
      status: "to_read",
      rating: null,
    },
    {
      id: 4,
      isbn: "9781449365035",
      name: "Speaking JavaScript",
      author: "Axel Rauschmayer",
      pages: 460,
      pagesRead: 230,
      status: "reading",
      rating: 4,
    },
    {
      id: 5,
      isbn: "9781491950296",
      name: "Building Microservices",
      author: "Sam Newman",
      pages: 282,
      pagesRead: 282,
      status: "completed",
      rating: 3,
    },
    {
      id: 6,
      isbn: "9781617291999",
      name: "Docker in Action",
      author: "Jeff Nickoloff",
      pages: 304,
      pagesRead: 0,
      status: "to_read",
      rating: null,
    },
    {
      id: 7,
      isbn: "9781617292422",
      name: "React in Action",
      author: "Mark Tielens Thomas",
      pages: 360,
      pagesRead: 180,
      status: "reading",
      rating: 4,
    },
    {
      id: 8,
      isbn: "9781617293986",
      name: "Spring in Action",
      author: "Craig Walls",
      pages: 520,
      pagesRead: 520,
      status: "completed",
      rating: 5,
    },
    {
      id: 9,
      isbn: "9781617294136",
      name: "Elasticsearch in Action",
      author: "Radu Gheorghe",
      pages: 364,
      pagesRead: 0,
      status: "to_read",
      rating: null,
    },
    {
      id: 10,
      isbn: "9781617295485",
      name: "TypeScript in Action",
      author: "Paul Temple",
      pages: 392,
      pagesRead: 200,
      status: "reading",
      rating: 4,
    },
    {
      id: 11,
      isbn: "9781838647131",
      name: "Hands-On Machine Learning",
      author: "Aurélien Géron",
      pages: 856,
      pagesRead: 856,
      status: "completed",
      rating: 5,
    },
    {
      id: 12,
      isbn: "9781838648121",
      name: "40 Algorithms Every Programmer Should Know",
      author: "Imran Ahmad",
      pages: 380,
      pagesRead: 0,
      status: "to_read",
      rating: null,
    },
  ],
};

type GoalDetailProps = {
  params: Promise<{
    id: string;
  }>;
  searchParams: Promise<{
    [key: string]: string | string[] | undefined;
  }>;
};

export default async function GoalDetail({ searchParams }: GoalDetailProps) {
  const _searchParams = await searchParams;
  const selectedStatus = _searchParams.status ?? null;

  const goal = mockGoal; // Replace with API call later
  // Filter books - this will be replaced with API filtering later
  const filteredBooks =
    selectedStatus != null
      ? goal.books.filter((book) => book.status === selectedStatus)
      : goal.books;

  return (
    <div className="flex flex-col min-h-screen p-8 sm:p-12 space-y-8">
      <BackButton label="Back to Goals" path="/goals" />
      {/* Goal Card */}
      <div className="p-6 border rounded-lg space-y-6">
        <div className="flex justify-between items-start">
          <div>
            <h1 className="text-4xl font-bold mb-2">{goal.name}</h1>
            <p className="text-gray-400">
              {goal.booksCount != null && `${goal.booksCount} books • `}
              {goal.pagesTotal} pages total
            </p>
          </div>
          <div className="text-right">
            <p className="text-2xl font-bold">
              {Math.round((goal.pagesRead / goal.pagesTotal) * 100)}%
            </p>
            <p className="text-gray-400">
              {goal.pagesRead} of {goal.pagesTotal}
            </p>
          </div>
        </div>
        <Progress value={(goal.pagesRead / goal.pagesTotal) * 100} />
        <div className="grid grid-cols-2 sm:grid-cols-4 gap-4">
          <div className="p-4 border rounded-lg">
            <p className="text-sm text-gray-400 mb-1">Balance</p>
            <p
              className={`text-lg font-bold ${
                goal.balance && goal.balance > 0
                  ? "text-green-500"
                  : goal.balance && goal.balance < 0
                  ? "text-red-500"
                  : ""
              }`}
            >
              {goal.balance ?? 0} pages
            </p>
          </div>
          <div className="p-4 border rounded-lg">
            <p className="text-sm text-gray-400 mb-1">Speed</p>
            <p className="text-lg font-bold">
              {goal.speedAverage ?? 0}
              <span className="text-gray-400 text-sm ml-1">
                / {goal.speedIdeal ?? 0}
              </span>
            </p>
          </div>
          <div className="p-4 border rounded-lg">
            <p className="text-sm text-gray-400 mb-1">Above Goal</p>
            <p className="text-lg font-bold text-green-500">
              {goal.daysAboveGoal ?? 0} days
            </p>
          </div>
          <div className="p-4 border rounded-lg">
            <p className="text-sm text-gray-400 mb-1">Below Goal</p>
            <p className="text-lg font-bold text-red-500">
              {goal.daysBelowGoal ?? 0} days
            </p>
          </div>
        </div>
      </div>
      {/* Books Section */}
      <div className="space-y-6">
        <div className="flex justify-between items-center">
          <h2 className="text-2xl font-bold">Books in this Goal</h2>
          <AddBookButton />
        </div>
        <StatusFilter selectedStatus={selectedStatus} />
        <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 xl:grid-cols-4 gap-4">
          {filteredBooks.map((book) => (
            <div
              key={book.id}
              className="flex gap-4 p-3 border rounded-lg transition-all duration-300 hover:shadow-lg hover:border-slate-400 dark:hover:border-slate-600"
            >
              <div className="relative w-[70px] h-[100px] rounded-lg overflow-hidden flex-shrink-0">
                <Image
                  alt={`${book.name} cover`}
                  src={`https://covers.openlibrary.org/b/isbn/${book.isbn}-M.jpg`}
                  fill
                  className="object-cover"
                />
              </div>
              <div className="flex flex-col min-w-0 flex-1">
                <div className="mb-2">
                  <Link
                    href={`/books/${book.id}`}
                    className="text-sm font-bold line-clamp-2 hover:text-slate-600 dark:hover:text-slate-300 transition-colors"
                  >
                    {book.name}
                  </Link>
                  <p className="text-xs text-gray-400 line-clamp-1">
                    {book.author}
                  </p>
                </div>
                <div className="mt-auto space-y-2">
                  <div>
                    <div className="flex items-center justify-between text-xs mb-1">
                      <span className="text-gray-400">Progress</span>
                      <span className="text-gray-400">
                        {Math.round(
                          (book.pagesRead / (book?.pages ?? 0)) * 100
                        )}
                        %
                      </span>
                    </div>
                    <Progress
                      value={(book.pagesRead / (book?.pages ?? 0)) * 100}
                    />
                    <p className="text-xs text-gray-400 mt-0.5">
                      {book.pagesRead} of {book.pages} pages
                    </p>
                  </div>
                  <div className="flex items-center justify-between">
                    <span
                      className={`
                      px-1.5 py-0.5 text-[10px] rounded-full
                      ${
                        book.status === "reading"
                          ? "bg-blue-500/10 text-blue-500"
                          : ""
                      }
                      ${
                        book.status === "to_read"
                          ? "bg-yellow-500/10 text-yellow-500"
                          : ""
                      }
                      ${
                        book.status === "completed"
                          ? "bg-green-500/10 text-green-500"
                          : ""
                      }
                    `}
                    >
                      {book.status.replace("_", " ")}
                    </span>
                    {book.rating && (
                      <div className="flex gap-0.5">
                        {[...Array(5)].map((_, i) => (
                          <svg
                            key={i}
                            className={`w-3 h-3 ${
                              i < book.rating!
                                ? "text-yellow-400"
                                : "text-gray-300"
                            }`}
                            fill="currentColor"
                            viewBox="0 0 20 20"
                          >
                            <path d="M9.049 2.927c.3-.921 1.603-.921 1.902 0l1.07 3.292a1 1 0 00.95.69h3.462c.969 0 1.371 1.24.588 1.81l-2.8 2.034a1 1 0 00-.364 1.118l1.07 3.292c.3.921-.755 1.688-1.54 1.118l-2.8-2.034a1 1 0 00-1.175 0l-2.8 2.034c-.784.57-1.838-.197-1.539-1.118l1.07-3.292a1 1 0 00-.364-1.118L2.98 8.72c-.783-.57-.38-1.81.588-1.81h3.461a1 1 0 00.951-.69l1.07-3.292z" />
                          </svg>
                        ))}
                      </div>
                    )}
                  </div>
                </div>
              </div>
            </div>
          ))}
        </div>
        {filteredBooks.length === 0 && (
          <div className="p-8 border rounded-lg text-center">
            <p className="text-gray-400 mb-4">
              {selectedStatus
                ? `No ${selectedStatus.replace("_", " ")} books in this goal`
                : "No books added to this goal yet"}
            </p>
            <button className="px-6 py-2 rounded-lg border transition-all duration-300 hover:shadow-lg hover:scale-[1.02] hover:border-slate-400 dark:hover:border-slate-600">
              {selectedStatus ? "Clear filter" : "Add your first book"}
            </button>
          </div>
        )}
      </div>
    </div>
  );
}
