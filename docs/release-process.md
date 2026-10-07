# Release process

Use this process to create and publish a plugin core release.

## 1. Generate and update release notes

Generate the release notes for the version being published, then update the repository release notes before tagging the release. These are the committed release notes that are published on the developer site.

Update:

- `morpheus-plugin-docs/src/docs/asciidoc/ReleaseNotes.adoc`

Use the existing AsciiDoc style in that file:

- Add the new version at the top of the release list.
- Format the version as `* **<version>**`.
- Group related changes under short emphasized headings.
- List individual changes under those headings.

Example:

```asciidoc
* **1.5.0**
** *Feature Area*
*** Summary of a notable change.
*** Summary of another notable change.
```

## 2. Tag the release

Release tags use the established `rel-<version>` format. Existing examples include:

- `rel-1.5.0`
- `rel-1.4.2`
- `rel-1.3.4`

Create an annotated release tag from the commit being released:

```sh
git tag -a rel-<version> -m "Release <version>"
```

For example:

```sh
git tag -a rel-1.5.0 -m "Release 1.5.0"
```

## 3. Push the tag to GitHub

Push the release tag:

```sh
git push origin rel-<version>
```

## 4. Create the GitHub release

Create a GitHub release using the pushed tag.

Use the established release naming pattern:

- **Tag:** `rel-<version>`, for example `rel-1.5.0`
- **Release title:** `v<version>`, for example `v1.5.0`
- **Release contents:** copy the committed repository release notes from `ReleaseNotes.adoc` into the GitHub release body and reformat them into GitHub Markdown style.

Use the existing GitHub release style:

- Start with `### What's Changed`.
- Group related changes under short headings when there are multiple feature areas.
- Add a final `**Full Changelog**` link comparing the previous release tag to the new release tag.

Example release body:

```markdown
### What's Changed

* *Feature Area*
  * Summary of a notable change.
  * Summary of another notable change.

**Full Changelog**: https://github.com/HewlettPackard/morpheus-plugin-core/compare/rel-1.4.2...rel-1.5.0
```

Example using the GitHub CLI:

```sh
gh release create rel-<version> \
  --title "v<version>" \
  --notes-file <release-notes-file>
```

For example:

```sh
gh release create rel-1.5.0 \
  --title "v1.5.0" \
  --notes-file release-notes-1.5.0.md
```

## 5. Wait for publish automation

GitHub Actions picks up the release, creates the build, and publishes the build to Maven Central.

After the build and publish are complete, the release usually appears on Maven Central in approximately 15 minutes while it propagates through Maven Central systems.
