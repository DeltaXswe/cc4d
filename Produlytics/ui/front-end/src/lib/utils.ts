type Ord = number | string | boolean;

export function compare(a: Ord, b: Ord, isAsc: boolean) {
  return (a < b ? -1 : 1) * (isAsc ? 1 : -1);
}
