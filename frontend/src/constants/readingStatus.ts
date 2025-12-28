import type { ReadingStatus } from "@/types";

export const READING_STATUS_LABELS: Record<ReadingStatus["status"], string> = {
  "Not started": "Noch nicht begonnen",
  Reading: "Wird gelesen",
  Finished: "Gelesen",
};

export function getReadingStatusLabel(status: string): string {
  const normalized =
    status.toLowerCase() === "not started" ? "Not started"
    : status.toLowerCase() === "reading" ? "Reading"
    : status.toLowerCase() === "finished" ? "Finished"
    : null;

  return normalized ? READING_STATUS_LABELS[normalized] : status;
}
