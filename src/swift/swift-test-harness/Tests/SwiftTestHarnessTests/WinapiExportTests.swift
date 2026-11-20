#if canImport(Testing)
import Testing
import Winapi

@Suite("Winapi Swift Export Tests")
struct WinapiExportTests {
    @Test("Minwindef and Windef types and functions work from Swift")
    func testMinwindefAndWindef() throws {
        let word = shared.minwindef.MAKEWORD(a: 0x34, b: 0x12)
        #expect(word == 0x1234)
        #expect(shared.minwindef.LOBYTE(l: word) == 0x34)
        #expect(shared.minwindef.HIBYTE(l: word) == 0x12)

        let rect = shared.windef.RECT(left: 10, top: 20, right: 100, bottom: 200)
        #expect(rect.left == 10)
        #expect(rect.top == 20)
        #expect(rect.right == 100)
        #expect(rect.bottom == 200)
    }

    @Test("GUID equality works from Swift")
    func testGuid() throws {
        let nullGuid = shared.guiddef.IID_NULL
        #expect(shared.guiddef.IsEqualGUID(g1: nullGuid, g2: nullGuid))
    }
}
#elseif canImport(XCTest)
import XCTest
import Winapi

final class WinapiExportTests: XCTestCase {
    func testMinwindefAndWindef() throws {
        let word = shared.minwindef.MAKEWORD(a: 0x34, b: 0x12)
        XCTAssertEqual(word, 0x1234)
        XCTAssertEqual(shared.minwindef.LOBYTE(l: word), 0x34)
        XCTAssertEqual(shared.minwindef.HIBYTE(l: word), 0x12)

        let rect = shared.windef.RECT(left: 10, top: 20, right: 100, bottom: 200)
        XCTAssertEqual(rect.left, 10)
        XCTAssertEqual(rect.top, 20)
        XCTAssertEqual(rect.right, 100)
        XCTAssertEqual(rect.bottom, 200)
    }

    func testGuid() throws {
        let nullGuid = shared.guiddef.IID_NULL
        XCTAssertTrue(shared.guiddef.IsEqualGUID(g1: nullGuid, g2: nullGuid))
    }
}
#endif
