# examples

Clojure examples from clojuredocs.org for when `doc` is not enough!

[![](https://clojars.org/examples/latest-version.svg)](https://clojars.org/examples)

## Usage

- Download phantomjs
- Create an environment variable `PHANTOMJS` that points to its executable
- E.g. `(examples map)` will print all clojuredocs.org examples of `map`

Cache can be cleared with `(clear-examples-cache name)`.
If `name` is omitted, the entire cache will be cleared.

## License

Copyright © 2016 Dario Oddenino

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
